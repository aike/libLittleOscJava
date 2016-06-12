// Osc.java by aike
// licenced under MIT License. 

package net.aikelab.liblittleosc;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.nio.ByteBuffer;

public class Osc {
    private DatagramSocket sock_;
    private DatagramPacket packet_;
    private boolean socketready_;
    private InetAddress addr_;
    private String host_;
    private int port_;
    private String message_;

    private ArrayList<Byte> sendList_;
    private ArrayList<Byte> dataList_;

    Osc() {
        socketready_ = false;
        port_ = 0;
        sendList_ = new ArrayList<Byte>();
        dataList_ = new ArrayList<Byte>();
        message_ = "";
        socketready_ = false;
    }

    public void SetHost(String host) {
        host_ = host;
        new Thread(new Runnable() {
            public void run() {
            try {
                addr_ = InetAddress.getByName(host_);
                //addr_ = java.net.Inet6Address.getByName(host_);
                sock_ = new DatagramSocket();
                socketready_ = true;
            } catch (Exception e) {
                socketready_ = false;
            }
            }
        }).start();
    }

    public String GetHost() { return host_; }

    public void SetPort(int port) { port_ = port; }

    public boolean IsReady() { return socketready_; }

    public void ResetReadyFlag() { socketready_ = false; }

    public String GetMessage() { return message_; }

    public void Send() {
        while (!socketready_) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sendList_.add((byte)0x00);
        fill4byte();
        sendList_.addAll(dataList_);

        Thread sendProc = new Thread(new Runnable() {
            public void run() {
                byte buf[] = new byte[sendList_.size()];
                for (int i = 0; i < sendList_.size(); i++) {
                    buf[i] = sendList_.get(i);
                }

                try {
                    packet_ = new DatagramPacket(buf, buf.length, addr_, port_);
                    sock_.send(packet_);
                } catch (Exception e) {
                    System.err.println("Exception : " + e);
                }
            }
        });
        sendProc.start();
        try {
            sendProc.join();
        } catch (Exception e) {
            System.err.println("Exception : " + e);
        }
    }

    public void PushAddress(String adrs) {
        ClearArg();
        byte[] bytes = adrs.getBytes();
        for (byte b : bytes) {
            sendList_.add(b);
        }
        sendList_.add((byte)0x00);
        fill4byte();
        sendList_.add((byte)0x2C);
        message_ += adrs;
    }

    public void PushArg(int arg) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(arg).array();
        for (byte b : bytes) {
            dataList_.add(b);
        }
        sendList_.add((byte)0x69);
        message_ += " " + arg;
    }

    public void PushArg(float arg) {
        byte[] bytes = ByteBuffer.allocate(4).putFloat(arg).array();
        for (byte b : bytes) {
            dataList_.add(b);
        }
        sendList_.add((byte)0x66);
        message_ += " " + arg;
    }

    public void PushArg(String arg) {
        byte[] bytes = arg.getBytes();
        for (byte b : bytes) {
            dataList_.add(b);
        }
        dataList_.add((byte)0x00);
        while (dataList_.size() % 4 != 0) {
            dataList_.add((byte)0x00);
        }
        sendList_.add((byte)0x73);
        message_ += arg;
    }

    public void ClearArg() {
        sendList_.clear();
        dataList_.clear();
        message_ = "";
    }

    private void fill4byte() {
        while (sendList_.size() % 4 != 0) {
            sendList_.add((byte)0x00);
        }
    }

}

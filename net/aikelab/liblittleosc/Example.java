// Example.java by aike
// licenced under MIT License. 

package net.aikelab.liblittleosc;

public class Example {
	
	Example() {
	}
	
	public static void main(String[] args) {
		Osc osc = new Osc();

		osc.SetHost("localhost");
		osc.SetPort(10000);

		osc.PushAddress("/hello");
		osc.Send();

		osc.PushAddress("/hello");
		osc.PushArg(100);
		osc.Send();

		osc.PushAddress("/hello");
		osc.PushArg(100);
		osc.PushArg(1.5f);
		osc.Send();

		osc.PushAddress("/hello");
		osc.PushArg("world");
		osc.PushArg(100);
		osc.PushArg(1.5f);
		osc.Send();

	}
}

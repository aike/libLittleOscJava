libLittleOsc
====
Java simple OSC messaging library

##FEATURES
- send OSC packet over UDP
- support int32, float32 and string parameters

##Package
```
import net.aikelab.liblittleosc.Osc;
```

##Example
```
 Osc osc = new Osc();
 osc.PushAddress("/hello");
 osc.PushArg("world");
 osc.PushArg(100);
 osc.PushArg(1.5f);
 osc.Send();
```

##WEBSITE
http://github.com/aike/liblittleosc

##CREDIT
libLittleOsc program is licenced under MIT License.  
Contact: twitter @aike1000

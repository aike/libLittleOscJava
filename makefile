PKG=net/aikelab/liblittleosc
.SUFFIXES: .java .class

all: $(PKG)/Example.class $(PKG)/Osc.class

run: $(PKG)/Example.class $(PKG)/Osc.class
	java net.aikelab.liblittleosc.Example

clean:
	rm -f $(PKG)/*.class

.java.class:
	javac $*.java

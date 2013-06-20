Compile: 
	javac GAInterface.java Server.java Client.java
	rmic Server
	javah -jni Server
	gcc -fPIC -c Server.c -I/usr/local/jdk/include -I/usr/local/jdk/include/linux
	ld -shared -o libServer.so Server.o
	rm -f *.o
	
runServer:
	java -Djava.library.path=. Server

runClient:
	java Client 1337

Clean: 
	rm *.o
	rm *.so
	rm *.class


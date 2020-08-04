//code by Mik-el
import java.io.*;
import java.net.*;

class ServerHandlerConcorrente extends ServerHandler implements Runnable{
	private Thread th;
	public ServerHandlerConcorrente(Socket soc) {
		super(soc);
		th = new Thread(this);
	}

	public void run() {
		super.handle();
	}

	public void handle(){
		th.start();
	}
}
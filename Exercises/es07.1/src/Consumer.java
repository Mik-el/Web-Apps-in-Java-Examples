//code by Mik-el
public class Consumer extends Thread {
	
	
	private BoundedBuffer buff;

	//costruttore, accetta come parametro il BoundedBuffer
	public Consumer(ThreadGroup t, String name, BoundedBuffer b) { 
		super(t, name);
		buff = b; 
	}
	
	
	//thread consumer fino a quando non è interrotto  stampa a video i contenuti del buffer 
	public void run() {
			try {
					while(!isInterrupted())
						System.out.println(buff.get());
			} 
			catch (InterruptedException e) {	
				
			}
			System.out.println("Il thread Consumer sta per terminare");
	}
}

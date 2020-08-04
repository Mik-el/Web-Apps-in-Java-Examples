//code by Mik-el
public class Producer extends Thread {
	
	
	private BoundedBuffer buff;
	
	//costruttore, accetta come parametro un BoundedBuffer
	public Producer(ThreadGroup t, String nome, BoundedBuffer b) {
		super(t, nome);
		buff = b; 
	}
	
	
	//fino a quando il thread Producer non è interrotto continua ad aggiungere un intero++ al buffer
	public void run() {
		int k = 0;
		
		try {
				while (	! isInterrupted()	)
				buff.put(k++);
			 } 
		
		catch (InterruptedException e) {
				System.out.println("Il thread Producer sta per terminare");
		}
	}
	
}
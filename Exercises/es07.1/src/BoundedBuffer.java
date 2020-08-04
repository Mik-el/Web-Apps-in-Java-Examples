//code by Mik-el
public class BoundedBuffer {
	
		private static final int n = 10;
		private int[] buff = new int[n]; //buffer 10 interi
		private int front, rear, indice; //indici inizio, fine, corrente
		
		
		//inserisce intero nel buffer
		public synchronized void put( int el ) throws InterruptedException {
		while(indice == n) wait(); //se indice corrente ==10, quindi buffer pieno, wait
		buff[rear] = el; //in posizione rear ci mettiamo el 
		rear = (rear +1) % n; //aggiornamento indice rear
		indice++;
		notifyAll();
		}
	
		
		//restituisce intero dal buffer
		public synchronized int get() throws InterruptedException {
		while(indice == 0) wait(); //se indice corrente ==0, quindi buffer vuoto wait
		int temp = buff[front]; //temp ha il valore del buffer in posizione front
		front = (front+1) % n; //aggiornamento indice front
		indice--;
		notifyAll();
		return temp;
		}
}

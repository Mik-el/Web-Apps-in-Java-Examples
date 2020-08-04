//code by Mik-el
import java.util.Scanner;

public class ProConTest {
	
	
	
	
public static void main(String args[]) {
	
	BoundedBuffer b = new BoundedBuffer();
	ThreadGroup tg= new ThreadGroup("ProConGroup");
	
	//String flag = null;
	new Producer(tg, "producer", b).start();
	new Consumer(tg, "consumer", b).start();
	
	Scanner sc= new Scanner(System.in);
	String risposta;
	
	do{
		System.out.println("Posso chiudere l'applicazione? SI/NO");
		risposta= sc.next();
	}while (  !risposta.equalsIgnoreCase("si") );
	
	tg.interrupt();
	sc.close();
	}
}
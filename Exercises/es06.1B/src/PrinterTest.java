//code by Mik-el
import java.io.PrintStream;

public class PrinterTest {
			public static void main (String argv[]) {
				
				//la classe PrinterTest simile alla versione precedente solo che con approccio ai thread

				Thread p1 = new Thread(new PrinterR("Sono il thread 1"));
				Thread p2 = new Thread(new PrinterR("Sono il thread 2"));
				Thread p3 = new Thread(new PrinterR("Sono il thread 3"));
				
				p1.start();
				p2.start();
				p3.start();
				
				
				
			
			}
		}
		
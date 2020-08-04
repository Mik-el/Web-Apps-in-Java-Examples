//code by Mik-el
public class PrinterTest {
			
			public static void main (String[] args) {
				
				Printer p1= new Printer ("Sono il thread 1");
				Printer p2= new Printer ("Sono il thread 2");
				Printer p3= new Printer ("Sono il thread 3");
				p1.start();
				p2.start();
				p3.start();
			}
		}

		
		
//code by Mik-el
 class Printer extends Thread{
	
	
	
	 private String mess;
	
	public Printer (String s ) { //costruttore che  accetta stinga, in questo caso a seconda del thread "sono il thread n 1 o 2 o 3"
		mess = s;
		
	}

	public void run () {				//implementiamo run() attraverso un ciclo	che va a scrivere un valore diverso sullo standart output ogni volta
		for (int i=0; i<100; i++) {
			System.out.println(i+ " "+ mess);
		}
		

	
}
 }

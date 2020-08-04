//code by Mik-el
 class PrinterR implements Runnable{
	
	private String mess;
	private Thread myThread ;
	
	public PrinterR (String s ) {
		mess = s;
		myThread = new Thread (this);
		
	}

	public void run () {
		for (int i=0; i<100; i++) {
			System.out.println(i+")  "+ mess);
		}
	}
	
	//sovrascrivo anche il metodo void
		public void start() {
			myThread.start();
		}

	

 }

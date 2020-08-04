//code by Mik-el
import java.io.*;
import java.util.*;
import java.net.*;

public class Room {

	//array list di oggetti PrintStream
	private ArrayList<PrintStream> lista = new ArrayList<PrintStream>(); 
	
	public Room() {} //costruttore vuoto
	
	//riceve messaggio come parametro,  
	public synchronized void broadcast(String msg) { 
		for(PrintStream out : lista)
			out.println(msg);
	}
	
	//metodo add aggiunge alla lista un oggetto Printstream ottenuto dall' output della socket passata come parametro
	public synchronized void add(Socket s) throws IOException {
			lista.add(new PrintStream(s.getOutputStream()));
	}
}

//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;

class StreamClient { 
	
	public static void main(String argv[]) throws Exception { 
		
		/*1---Acquisico i dati di una persona e restituisco un ogg di tipo persona*/
		Scanner inputUtente = new Scanner(System.in); 
		System.out.println("Inserisci i dati di una persona");
		System.out.print("Inserisci il nome: "); String firstName = inputUtente.nextLine();
		System.out.print("Inserisci il cognome: "); String lastName = inputUtente.nextLine();
		System.out.print("Inserisci l' eta' : "); byte age = inputUtente.nextByte();
		Person p = new Person(firstName, lastName, age);
		
		/*2---creo Socket del client*/
		Socket socketclient = new Socket("127.0.0.1", 2040);
		
		/*3---dallo stream di output della socket client creo un ogg di tipo ObjectOutputStream
		 * si quell' ogg scrivo il mio ogg Persona*/
		ObjectOutputStream versoServer = new ObjectOutputStream(socketclient.getOutputStream());
		versoServer.writeObject(p);
		
		/*4---dallo stream di input ricevuto creo un oggetto InputStream*/
		InputStream daServer = socketclient.getInputStream();
		
		/*5--- da questo ogg InputStream leggo il prossimo byte e mi comporto di conseguenza */
		byte result = (byte)daServer.read();
		if(result == 'o') System.out.println("Oggetto salvato su file dal lato server");
		else if(result == 'k') System.out.println("Oggetto non salvato su file dal lato server");
		
		/*5---chiusura di inputUtente, ogg ObjectOutputStream , ogg InputStream, socket*/
		inputUtente.close();
		versoServer.close();
		daServer.close();
		socketclient.close(); 
	} 
} 

class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	public Person(String fn, String ln, byte ag) {
		firstName = fn; lastName = ln; age = ag;
	}
	
	public void setFirstName(String fn) { firstName = fn; }
	public String getFirstName() { return firstName; }
	public void setLastName(String ln) { lastName = ln; }
	public String getLastName() { return lastName; }
	public void setAge(byte ag) { age = ag; }
	public byte getAge() { return age; }
	public String toString() { return "firstName: " + firstName + "\nlastName: " + lastName + "\nage: " + age + "\n"; }
	private String firstName;
	private String lastName;
	private byte age;
}
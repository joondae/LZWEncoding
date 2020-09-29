import java.util.Scanner;

public class Main {
	public static void main(String [] args) {
		//Asks for file name/path for encoding
		Scanner keyboard = new Scanner(System.in);
		String fileToEncodeName;
		System.out.print ("Enter file name/path for encoding: ");
	    fileToEncodeName = keyboard.next();
	    keyboard.close();
	    
	    //Runs encoding algorithm and prints statistics and execution time
	    Encoder e = new Encoder(fileToEncodeName);
	    e.encode();
		
	    //Runs decoding algorithm and prints execution time
	    Decoder d = new Decoder("encodedFile.txt");
	  	d.decode();
	  	
	  	//Ensures compression and decompression is lossless
	  	System.out.print("Lossless? " + d.checkDecodedFile(fileToEncodeName, "decodedMessage.txt"));
	}
}

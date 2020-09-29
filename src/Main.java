import java.util.Scanner;

public class Main {
	public static void main(String [] args) {
		//Asks for file name/path for encoding
		Scanner keyboard = new Scanner(System.in);
		String fileToEncodeName;
		System.out.print ("Enter file name/path for encoding: ");
	    fileToEncodeName = keyboard.next();
	    keyboard.close();
	    
	    //Running encoding algorithm and printing statistics and execution time
	    Encoder e = new Encoder(fileToEncodeName);
	    e.encode();
		
	    //Running decoding algorithm and printing execution time
	    Decoder d = new Decoder("encodedFile.txt");
	  	d.decode();
	  	
	  	//Ensuring compression and decompression is lossless
	  	System.out.print("Lossless? " + d.checkDecodedFile(fileToEncodeName, "decodedMessage.txt"));
	}
}

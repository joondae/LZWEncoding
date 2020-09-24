import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//Nathan Thomas and Grant Bishop
//Improvements by Maya Doyle and Anneliese Ardizzone ʕ•́ᴥ•̀ʔb


public class Encoder{
	private static String fileName; //This variable is the name of the file.
	private ArrayList<Integer> encodedValues = new ArrayList<Integer>(); //This variable is the key of encoded characters.
	

	//--------------------------------------------------------------------------------------------------------------------

	public void Encode (String inputString) throws IOException {
		HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
		int max=512; //maximum dictionary size
		int dictionarySize=256; //current # of items in the dictionary
		
		//This pre-fills the hashmap with ascii characters
		for (int i = 0; i < 256 ; i++){ 
			dictionary.put("" + (char) i, i); 
		}


		//The below method reads char by char from a string given at the start to create an array list of Integers called encodedValues. It also fills the dictionary.
		String temp = ""; //This string will be used to cycle through inputString. (This is the "previous character/string".)
		for (char start : inputString.toCharArray()) { //This line converts the input into an array of chars and cycles through it char by char
			String symbol = temp + start;
			if (dictionary.containsKey(symbol)){ //This checks to see if the symbol is already in the dictionary. If it is, temp is set to symbol.
				temp = symbol;
			}
			else{
				encodedValues.add(dictionary.get(temp)); //If the symbol isn't in the dictionary, it is added.
				if (dictionarySize < max){ 
					dictionary.put(symbol,dictionarySize++); 
				}
				else{ //If the dictionary is full, the below line is printed to alert the user.
					System.out.println ("Dictionary is full. Errors may occur.");
				}
				temp = "" + start;
			}
		}

		//This ensures that the last symbol isn't skipped when encoding.
		if (!temp.equals(""))
			encodedValues.add(dictionary.get(temp));

		//The below creates a new file called fileName
		printCodestreamAndStatistics(fileName);
	}


	//--------------------------------------------------------------------------------------------------------------------
	
	//The below method writes each Integer in encodedValue to encodedFile.txt
	//and prints codestream size, original and encoded file sizes (bytes),
	//compression ratio, and space savings
	public void printCodestreamAndStatistics(String inputFile) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("encodedFile.txt")));
		for(int i = 0; i < encodedValues.size(); i++)
		{
			bw.append("" + encodedValues.get(i) + " ");
		}
		
		bw.close();
		
		System.out.println("codstream size: " + encodedValues.size());
		
		File encodedFile = new File("encodedFile.txt");
		File originalFile = new File(fileName);
		System.out.println("\nOriginal file size (bytes): " + originalFile.length());
		System.out.println("Encoded file size (bytes): " + encodedFile.length());
		System.out.println("\nCompression ratio: " + ((double) originalFile.length()/encodedFile.length()));
		System.out.println("Space savings: " + (1 - ((double) encodedFile.length()/originalFile.length())));
	}

	//--------------------------------------------------------------------------------------------------------------------
	
	public static void main(String[]args) throws IOException{ //The below code runs the other methods.
		Scanner keyboard = new Scanner(System.in);
    	System.out.print ("Enter file name/path for encoding: ");
    	fileName = keyboard.nextLine();

		Encoder en = new Encoder();
		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		int temp;
		String input="";
		while ((temp=br.read())!=-1){
			input+=(char)temp;
		}
		en.Encode(input);
		
	}
}
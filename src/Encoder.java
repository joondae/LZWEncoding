import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//Nathan Thomas and Grant Bishop
//Improvements by Maya Doyle and Anneliese Ardizzone ʕ•́ᴥ•̀ʔb
//Optimized by Max Hahn and Reginaldo Kim

public class Encoder{
	private static String fileName;
	private final int maxDictionarySize = 512;

	public Encoder(String fileToEncodeName) {
		fileName = fileToEncodeName;
	}
	
	//This method contains all helper methods used to encode given file
	//and prints method execution time
	public void encode () {
		long startTime = System.nanoTime();
		ArrayList<Integer> codestream = createCodestream(initializeDictionary());
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		
		printStatistics(codestream);
		System.out.println("Encoding execution time (milliseconds): " + duration/1000000);
	}

	//This method initializes the dictionary with ASCII characters whose decimal values range from 0-255
	private HashMap<String, Integer> initializeDictionary() {
		HashMap<String, Integer> dictionary = new HashMap<String, Integer>(maxDictionarySize);
		
		for (int i = 0; i < 256 ; i++){ 
			dictionary.put("" + (char) i, i); 
		}
				
		return dictionary;
	}
	
	//This method generates the list of encoded characters (according to LZW compression formula)
	//and prints them to encodedFile.txt AND to terminal console (the latter is for debugging)
	private ArrayList<Integer> createCodestream(HashMap<String, Integer> dictionary) {
		ArrayList<Integer> codestream = new ArrayList<Integer>();
		String previous = "";
		int dictionarySize = 256;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("encodedFile.txt")));
			
			while(br.ready() && dictionary.size() < maxDictionarySize) {
				char current = (char) br.read();
				
				//Is the string P + C present in the dictionary?
				if(dictionary.containsKey(previous + current)) {
					//P = concat(P,C)
					previous += current;
				}
				else {
					//output the code word which denotes P to the codestream
					codestream.add(dictionary.get(previous));
					bw.write(dictionary.get(previous) + " ");
					
					//add the string concat(P,C) to the dictionary
					dictionary.put(previous + current, dictionarySize++);
					
					//P = C
					previous = Character.toString(current);
				}
			}
			
			//accounts for last read
			codestream.add(dictionary.get(previous));
			bw.write(dictionary.get(previous) + " ");
			
			//if dictionary size reaches its limit, print error message and stop compression
			if(dictionary.size() >= maxDictionarySize) {
				System.out.println("Maximum dictionary size reached");
				
				while(br.ready()) {
					int uncompressedChar = br.read();
					codestream.add(uncompressedChar);
					bw.write(uncompressedChar + " ");
				}
			}
			
			//ensures that no output gets "stuck" in buffer
			br.close();
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return codestream;
	}
	
	
	//This method writes each Integer in encodedValue to encodedFile.txt
	//and prints codestream size, original and encoded file sizes (bytes),
	//compression ratio, and space savings
	public void printStatistics(ArrayList<Integer> codestream) {
		System.out.println("\n\"Encoding Statistics\":\ncodestream size: " + codestream.size());
		
		File encodedFile = new File("encodedFile.txt");
		File originalFile = new File(fileName);
		System.out.println("\nOriginal file size (bytes): " + originalFile.length());
		System.out.println("Encoded file size (bytes): " + encodedFile.length());
		System.out.println("\nCompression ratio: " + ((double) originalFile.length()/encodedFile.length()));
		System.out.println("Space savings: " + (1 - ((double) encodedFile.length()/originalFile.length())) + "\n\n");
	}
}
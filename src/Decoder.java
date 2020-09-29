//Maya Doyle and Anneliese Ardizzone uwu

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Decoder{
	private final int maxDictionarySize = 512;
	private static String fileName;
	
	public Decoder(String encodedFileName) {
		fileName = encodedFileName;
	}
	
	//This method contains all helper methods used to decoded given file
	//and prints method execution time
	public void decode() {
		long startTime = System.nanoTime();
		writeToFile(decodeCodestream(initializeDictionary(), catalogueCodestream()));
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		
		System.out.println("Decoding execution time (milliseconds): " + duration/1000000);
	}
	
	//This method initializes the dictionary with ASCII characters whose decimal values range from 0-255
	private HashMap<Integer, String> initializeDictionary() {
		HashMap<Integer, String> dictionary = new HashMap<Integer,String>(maxDictionarySize);
		
		for(int i = 0; i < 256; i++) {
			dictionary.put(i, "" + (char) i);
		}
		
		return dictionary;
	}
	
	//This method reads the encoded file (contains the codestream)
	//and transfers all codes (Strings) into an ArrayList<Integer>
	private ArrayList<Integer> catalogueCodestream() {
		ArrayList<Integer> codestream = new ArrayList<Integer>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			
			String line = "";
			if((line = br.readLine()) != null) {
				for(String code : line.split(" ")) {
					codestream.add(Integer.parseInt(code));
				}
			}
			
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//For debugging only (check that Decoder's codestream is the same as Encoder's codestream)
		//System.out.println("codestream size: " + codestream.size());
		
		return codestream;
	}
	
	//This method decodes the codestream and rebuilds the dictionary
	private StringBuffer decodeCodestream(HashMap<Integer, String> dictionary, ArrayList<Integer> codestream) {
		int dictionarySize = 256;
		String w = "" + (char) (int) codestream.remove(0);
		StringBuffer decodedCodestream = new StringBuffer(w);
		
		for(int k : codestream) {
			String entry;
			
			if(dictionary.containsKey(k)) {
				entry = dictionary.get(k);
			}
			else if(k == dictionarySize) {
				entry = w + w.charAt(0);
			}
			else {
				throw new IllegalArgumentException("Bad compressed k: " + k);
			}
			
			decodedCodestream.append(entry);
			dictionary.put(dictionarySize++, w + entry.charAt(0));
			w = entry;
		}
		
		return decodedCodestream;
	}
	
	//This method writes the decoded codestream to "decodedMessage.txt"
	private void writeToFile(StringBuffer decodedCodestream) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("decodedMessage.txt")));
			
			bw.write(decodedCodestream.toString());
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//This method checks the decoded file with the file that was originally encoded
	//i.e. ensures that compression and decompression was lossless
	public boolean checkDecodedFile(String fileName1, String fileName2) {
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(new File(fileName1)));
			BufferedReader br2 = new BufferedReader(new FileReader(new File(fileName2)));
			
			while(br1.ready() && br2.ready()) {
				if(br1.read() != br2.read()) {
					br1.close();
					br2.close();
					
					return false;
				}
			}
			
			br1.close();
			br2.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
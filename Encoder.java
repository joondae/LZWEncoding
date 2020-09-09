import java.util.*;
import java.io.*;
public class Encoder{
	private static String fileInput = null;
	private static String fileName;
	
	public static void Encode_string(String inputString) throws IOException {

		HashMap<String, Integer> table = new HashMap<String, Integer>();
		int tableSize=255;
		for (int i = 0; i < 255 ; i++){
			table.put("" + (char) i, i); //pre-fills the hashmap with ascii characters
		}

		String temp = ""; //will be used to cycle through inputString
		ArrayList<Integer> encodedValues = new ArrayList<Integer>(); //key of encoded characters

		for (char start : inputString.toCharArray()) {
			String symbol = temp + start;
			if (table.containsKey(symbol)){ //checks to see if the symbol is already in the table
				temp = symbol;
			}else{
				encodedValues.add(table.get(temp)); //if the symbol isn't in the table it is added
				table.put(symbol,tableSize++);
				temp = "" + start;
			}
		}

		if (!temp.equals("")) //making sure the last symbol doesn't get skipped
			encodedValues.add(table.get(temp));
		
		CreateLZWfile(encodedValues); 
		
	}
}
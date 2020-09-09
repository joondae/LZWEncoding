import java.util.*;
import java.io.*;
public class Encoder{
	private static String fileInput = null;
	private static double maxSize; //Max Table size is based on the bit length input.
	private static String LZWFileName;
	

	/** Compress a string to a list of output symbols and then pass it for compress file creation.
	 * @param input_string //Filename that is used for encoding. */
	
	public static void Encode_string(String input_string) throws IOException {
	
		maxSize = 69;	
			
		double tableSize =  255;
		
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		
		for (int i = 0; i < 255 ; i++){
			table.put("" + (char) i, i);
		}

		String initString = "";
		
		ArrayList<Integer> encodedValues = new ArrayList<Integer>();
		
		for (char symbol : inputString.toCharArray()) {
			String symbol = initString + symbol;
			if (tableSize.containsKey(symbol))
				initString = symbol;
			else {
				encodedValues.add(table.get(initString));
			
				if(tableSize < maxSize)
					table.put(symbol, (int) table_Size++);
				initString = "" + symbol;
			}
		}

		if (!initString.equals(""))
			encodedValues.add(table.get(initString));
		
		CreateLZWfile(encodedValues); 
		
	}
}
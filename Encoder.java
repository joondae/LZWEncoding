//Nathan Thomas and Grant Bishop
import java.util.*;
import java.io.*;
public class Encoder
{
private static String fileName;
private ArrayList<Integer> encodedValues = new ArrayList<Integer>(); //key of encoded characters
public void Encode (String inputString) throws IOException {
	
	HashMap<String, Integer> table = new HashMap<String, Integer>();
	int max=500; //maximum hashmap size
	int tableSize=256;
	
	for (int i = 0; i < 256 ; i++){
		table.put("" + (char) i, i); //pre-fills the hashmap with ascii characters
	}

	String temp = ""; //will be used to cycle through inputString
	

	for (char start : inputString.toCharArray()) { //converts the input into a array of chars and cycles through it
		String symbol = temp + start;
		if (table.containsKey(symbol)){ //checks to see if the symbol is already in the table
			temp = symbol;
		}else{
			encodedValues.add(table.get(temp)); //if the symbol isn't in the table it is added
			if (tableSize<max){
				table.put(symbol,tableSize++);
			}
			temp = "" + start;
		}
	}

	if (!temp.equals("")) //making sure the last symbol doesn't get skipped
		encodedValues.add(table.get(temp));

	
	createFile("LZW.txt");
}
	public void createFile(String inputFile) throws IOException //creates a new file and outputs the encoded values
	{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("LZW" + inputFile));
		for(int i = 0;i<encodedValues.size();i++)
		{
			bw.append("" + encodedValues.get(i) + " ");
		}
		bw.close();
		
	}
	
	public static void main(String[]args) throws IOException //runs it
	{
		Encoder en = new Encoder();
		BufferedReader br = new BufferedReader(new FileReader("LZW.txt"));
		int temp;
		String input="";
		while ((temp=br.read())!=-1){
			input+=(char)temp;
		}
		en.Encode(input);
		
	}
		
}
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;


public class Encoder
{
private static String fileName;
private static int test=0;
private static String input="";
private ArrayList<Integer> encodedValues = new ArrayList<Integer>(); //key of encoded characters
public void Encode (String inputString) throws IOException {
	
	HashMap<String, Integer> table = new HashMap<String, Integer>();
	int max=500; //maximum hashmap size
	int tableSize=255;
	
	for (int i = 0; i < 255 ; i++){
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

	if (!temp.equals("")&&!table.containsKey(temp)) //making sure the last symbol doesn't get skipped
		encodedValues.add(table.get(temp));

	test=encodedValues.get(0);
	
	createFile("LZW.txt");
}
	public void createFile(String inputFile) throws IOException
	{
		
		PrintWriter pw = new PrintWriter(new File("LZW" + inputFile));
		for(int i = 0;i<encodedValues.size();i++)
		{
			pw.write(encodedValues.get(i));
		}
		
	}
	public void convertToString() throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("LZW.txt"));
		int temp;
		while ((temp=br.read())!=-1){
			input+=(char)temp;
		}
	}
	
	public static void main(String[]args) throws IOException
	{
		Encoder en = new Encoder();
		en.Encode(input);
		System.out.print(test);
		
	}
		
}
		
	

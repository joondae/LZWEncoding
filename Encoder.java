import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class Encoder
{
	public static void main(String[]args) throws FileNotFoundException
	{
		String outputFile=null;
		String inputFile=null;
		PrintWriter pw = new PrintWriter(new File(outputFile));
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		
	}
}
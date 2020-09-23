//Maya Doyle and Anneliese Ardizzone uwu

import java.util.*;
import java.io.*;

public class Decoder{
	public static void main (String[] args){
		//Creating variables
		String encodedFileName = "";
		String theStringToOutput = "";
		ArrayList<String> newDictionary = new ArrayList<String>();
		int placeInDictionary = 256;
		String addingToDictionary = "";

		//Pre-filling the dictionary to make later code easier to understand
		for (int u = 0; u < 256; u++){
			newDictionary.add("" + (char)(u));
		}

		//Getting encoded file name from user
		Scanner keyboard = new Scanner(System.in);
    	System.out.print ("Enter file name/path for decoding: ");
    	encodedFileName = keyboard.nextLine();

    	//Reading in the encoded file's contents to a string
    	String encodedFileContents = ""; 
		try{
			FileReader fileRead = new FileReader (encodedFileName);
			char currentLetterInEncodedFileContents = (char)(fileRead.read());
			while ((int)currentLetterInEncodedFileContents < 512){ //Less than 512 because the maximum dictionary length is 512 (meanig the greatest possible number is 511)
				encodedFileContents += currentLetterInEncodedFileContents;
				currentLetterInEncodedFileContents = (char)(fileRead.read());
			}
			fileRead.close();
		}
		catch(IOException ex){
			System.out.println ("File not found."); 
		}


		//Splitting encodedFileContents into each individual Strings for each number as opposed to one giant String
		String[] eachNumber = encodedFileContents.split(" ");

		//Converting each of the smaller Strings holding each number from Strings to ints
		ArrayList<Integer> theEncodedNumbers = new ArrayList<Integer>();
		for (int k = 0; k < eachNumber.length; k++){
			theEncodedNumbers.add(Integer.parseInt(eachNumber[k]));
		}

		//Loop through each encoded value.
			//If the encodedValue < 256 
				//theStringToOutput += (char)(theEncodedNumbers.get(index))
			//If the encodedValue is present in newDictionary
				//theStringToOutput += newDictionary(theEncodedNumbers.get(index - 256))
			//Else (if it's not <256 and not present in the dictionary)
				//theStringToOutput += theEncodedNumbers[index - 3] + theEncodedNumbers[index- 2] (because theEncodedNumbers[i] would be the previous two combined. Are -3 and -2 the right numbers to use?)
				//newDictionary.add(encodedValues[i])
			String currentDecodedValue = "";
			String previousDecodedValue = "";
			String firstCharOfCurrentDecodedValue;
			int previousCode;
			int currentCode;

			//The below code essentially recreates the dictionary and creates the output in the same was you would decode it by hand. It was way harder to write than I thought it would be :')
			previousCode = theEncodedNumbers.get(0);
			theStringToOutput += "" + (char)(previousCode);

			for (int k = 1; k < theEncodedNumbers.size(); k++){
				currentCode = theEncodedNumbers.get(k);
				previousDecodedValue = "" + (newDictionary.get(previousCode));
				if (currentCode >= newDictionary.size()){ //This if statements just fixes a weird error that sometimes happens when you have the current code right after itself
					currentDecodedValue = previousDecodedValue + previousDecodedValue.substring(0,1);
				}
				else{
					currentDecodedValue = "" + (newDictionary.get(currentCode));
				}
				theStringToOutput += currentDecodedValue;
				firstCharOfCurrentDecodedValue = currentDecodedValue.substring(0,1); //there's probably a better way to get the first char but this works and it's fast so ¯\_(ツ)_/¯
				newDictionary.add(previousDecodedValue + firstCharOfCurrentDecodedValue);
				previousCode = currentCode;
			}

		//Print the decoded message and write a file containing the decoded numbers.
		System.out.println("Below is your decoded message. You can also find a file named 'decodedMessage.txt' in the folder containing this Java file.");
		System.out.println(theStringToOutput);

		try {
			FileWriter writeToFile = new FileWriter("decodedMessage.txt");
			writeToFile.write(theStringToOutput);
			writeToFile.close();
		    } 
		catch (IOException e) {
			System.out.println("IOException");
		}
	}
}
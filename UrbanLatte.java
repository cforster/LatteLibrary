package ashley;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/* Urban Lib 
 * Ashley Lewis and Sara Pardee 
 * 5.23.17 
 * 
 * Given the definition of a word returns urban dictionary's definition 
 */

public class UrbanLatte {
	public static void main(String[] args) throws UnirestException {

//		String test = "[{'definition':'A person who is very hot";
//		for(int i = 0; i <0; i++) {
//			if(test=="defintion"){
//				System.out.println(test.substring(i+1));
//			}
//		}
//		
		// declarations:
		//ArrayList<String> definition = new ArrayList<String>(); //definiton of the word
		String definition = "";
		String userinput = " "; //what the user is inputting in
		Scanner sc = new Scanner(System.in);//import scanner
		String output = " "; //holds the api's output

		//Introduction: 
		System.out.println("Input the word you want to define!");
		userinput = sc.nextLine(); //takes in the user input and searches the api for it 

		// These code snippets use an open-source library.
		// http://unirest.io/java
		HttpResponse<String> response = Unirest
				.get("https://mashape-community-urban-dictionary.p.mashape.com/define?term=" + userinput) //searches based on the user input 
				.header("X-Mashape-Key", "a8MFoQ3SvZmshOxFFatgTIKRc2VAp1tSWobjsnRSuOt4ir0WxB")
				.header("Accept", "text/plain").asString();

		output = response.getBody(); //output of the api set to a string

		definition = getDef(output); //calls this function and sets it as as this string 

		//System.out.println(response.getBody()); //prints out user input


		System.out.println("Urban Dictionary's definition of " + userinput + " is:  " + System.lineSeparator()+ definition); //returns the answer


	}
	public static String getDef(String output) { //essentially we need are trying to find the definition; and return that to print it out; end at end quotes 
		
		String def = output.substring(output.indexOf("definition")+13,output.indexOf("permalink")-3);
		def= def.replaceAll("[.!?]\\s*", "\n");
		return def; 
	}
}

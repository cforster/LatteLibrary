package Latte;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

//@author Jason Friedman and Celia Heath 
//date 5/23/17
//This api takes a number as input and gives an interesting fact about that number as output (categories of facts include trivia, math, date, or year and the default is trivia) 

public class NumbersLatte {
	private int number;
	private String answer;
	private String type;
			
	public int number() {
		return number;
	}
	
	public String answer() {
		return answer;
	}
	
	public String type() {
		return type;
	}

	//get a random number:
	public static NumbersLatte fetch() {
		NumbersLatte ret = null;
		Random gen = new Random();
		while(ret==null) {
			ret = fetch(gen.nextInt(100), "");

		}
		return ret;
	}

	//given a number and a type (trivia, date, year, math (trivia is default): 
	public static NumbersLatte fetch(int num, String type) {

		String uri = "http://numbersapi.com/" + num + "/" + type;
		try {


			URLConnection yc = new URL(uri).openConnection();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							yc.getInputStream()));
			
			
			NumbersLatte l = new NumbersLatte();
			l.number = num;
			l.answer = in.readLine();
			l.type = type;
			
			in.close();

			return l;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

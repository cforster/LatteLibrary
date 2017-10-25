import java.util.Random;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class YodaLatteJM {

	public static void main(String[] args) {
		YodaLatteJM yoda = YodaLatteJM.fetch();
		System.out.println(yoda.yodafied);

	}
	//figure our how to turn the user input into a good format (use a string / regex to replace the spaces with pluses
	//input 

	String yodafied;
	//when they do an input
	public static YodaLatteJM fetch(String input) {

		String input1 = input.replaceAll(" ", "+");

		HttpResponse<String> response;
		try {
			response = Unirest.get("https://yoda.p.mashape.com/yoda?sentence=" + input1)
					.header("X-Mashape-Key", "YZjBsMdV33mshrLGCo584TVy0CCSp1yh26jjsnI7AlM2fmz94r")
					.header("Accept", "text/plain")
					.asString();

			YodaLatteJM o = new YodaLatteJM();
			o.yodafied = response.getBody();
			return o;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} //class

	public static YodaLatteJM fetch() {

		//random	

		String [] quotes = {"When nine hundred years old you reach, look as good you will not.", "Truly wonderful, the mind of a child is.", "That is why you fail.",
				"A Jedi uses the Force for knowledge and defense, never for attack.", "Adventure. Excitement. A Jedi craves not these things.", "Judge me by my size, do you?",
				"Fear is the path to the dark side…fear leads to anger…anger leads to hate…hate leads to suffering.", "Wars not make one great.", 
				"Luminous beings are we…not this crude matter.", "Do. Or do not. There is no try."};

		Random random = new Random();
		int r = random.nextInt(quotes.length);
		
		YodaLatteJM j = new YodaLatteJM();
		j.yodafied = quotes[r];
		return j;

	} //random class
	

}








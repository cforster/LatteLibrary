/*
 * Oliver Fisher and Spencer Gallant
 */


package CS3A;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class LatteOF {

	String title;
	String publisher;
	static int randomInt = 0;
	static ArrayList<String> randsubs = new ArrayList<String>();

	public static LatteOF fetch(String data)
	{

		try {
			String yoursubreddit = "";

			Document doc = Jsoup.connect("https://www.reddit.com/r/" + data + ".xml").get();

			Element link =  doc.select("title").get(1);
			String slink = link + "";
			String slink2 = slink.substring(7,slink.length() - 8);


			LatteOF o = new LatteOF();


			o.title = slink.substring(7,slink.length() - 8);

			return o;

		}
		catch (IOException e)
		{
			System.out.println("whoops, that is not a current subreddit!");
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("OOB");
		}
		return null;
	}

	public static LatteOF fetch2(String data){
		try {
			String yoursubreddit = "";

			Document doc = Jsoup.connect("https://www.reddit.com/r/" + data + ".xml").get();

			Element link2 = doc.select("title").get(2);
			String slink3 = link2 + "";
			String slink4 = slink3.substring(7,slink3.length() - 8);

			LatteOF f = new LatteOF();

			f.title = slink3.substring(7, slink3.length() - 8);
			return f;

		}
		catch (IOException e)
		{
			System.out.println("whoops, that is not a current subreddit!");
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("OOB");
		}
		return null;
	}

	//random subreddit
	public static LatteOF fetch3(String data){
		try {
			String yoursubreddit = "";





			Document doc = Jsoup.connect("https://www.reddit.com/r/" + data + ".xml").get();


			Element link3 = doc.select("title").get(2);
			String slink5 = link3 + "";
			String slink6 = slink5.substring(7,slink5.length() - 8);

			LatteOF s = new LatteOF();

			s.title = slink5.substring(7, slink5.length() - 8);
			return s;

		}
		catch (IOException e)
		{
			System.out.println("whoops, that is not a current subreddit!");
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("OOB");
		}
		return null;
	}


	public static void main(String[] args) 
	{
			Scanner scan = new Scanner(System.in);

			LatteOF o = null;
			LatteOF f = null;

			while(o==null) {
				System.out.println("Which subreddit would you like to display?");
				String yoursubreddit = scan.nextLine();
				String sub2 = yoursubreddit;
				o = LatteOF.fetch(yoursubreddit);
				f = LatteOF.fetch2(sub2);
			}

			//returns top 2 posts 
			System.out.println("1. " + o.title);
			System.out.println("2. " + f.title);

			System.out.println();
			
			String s1 = "NBA";
			String s2 = "NFL";
			String s3 = "ShowerThoughts";
			String s4 = "misleadingthumbnails";
			String s5 = "beamazed";
			String s6 = "HybridAnimals";
			String s7 = "advice";
			String s8 = "askhistorians";
			String s9 = "TalesFromThePizzaGuy";
			String s10 = "YouShouldKnow";

			randsubs.add(s1);
			randsubs.add(s2);
			randsubs.add(s3);
			randsubs.add(s4);
			randsubs.add(s5);
			randsubs.add(s6);
			randsubs.add(s7);
			randsubs.add(s8);			
			randsubs.add(s9);
			randsubs.add(s10);

			Random randomGenerator = new Random();
			randomInt = randomGenerator.nextInt(9);
			
			 //print random one
			 o = null;
			 f = null;

			while(o==null) {
				String yoursubreddit = randsubs.get(randomInt);
				String sub2 = yoursubreddit;
				o = LatteOF.fetch(yoursubreddit);
				f = LatteOF.fetch2(sub2);
			}

			//returns top 2 posts 
			System.out.println("the random subreddit is: " + randsubs.get(randomInt));
			System.out.println("1. " + o.title);
			System.out.println("2. " + f.title);
	}	
}

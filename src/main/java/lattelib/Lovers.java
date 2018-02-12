package lovelatte;


// ANNBELLE BUTTENWIESER, MICHELLE HUNNEWELL, KAITLIN YOUNG

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import javax.swing.text.Document;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Lovers {
	
	static Lovers l = new Lovers();

	public static void main(String[] args) throws UnirestException {
		
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input the first name.");
		l.name1 = scan.nextLine();
		System.out.println("Please input the second name.");
		l.name2 = scan.nextLine();
		System.out.println(fetch(l.name1, l.name2));
		
		
		
	}
	private static final String CACHESERVERADDR = "love.lattelib.com";
	//private static final List<String> compatibility = new ArrayList<>();
	
	String name1;
	String name2;
	String percent;
	String result; 
	



//	System.out.println("What is the first person's name?");
//	name1 = scan.nextLine;
//	System.out.println("What is the second person's name?");
//	name2 = scan.nextLine;
	
	public static Lovers fetch (String name1, String name2) throws UnirestException{
	
		
		// These code snippets use an open-source library. http://unirest.io/java
		HttpResponse<JsonNode> response = Unirest.get("https://love-calculator.p.mashape.com/getPercentage?fname=" + name1 + "&sname=" + "name2")
		.header("X-Mashape-Key", "M2Mz5x8fcpmshUrwqvo52LO5deqjp1e8tEEjsng79rdG6cFW9u")
		.header("Accept", "application/json")
		.asJson();
		 
		
		l.percent = response.getBody().getObject().get("percentage").toString();
		System.out.println(l.percent);
		l.result = response.getBody().getObject().get("result").toString();
		System.out.println(l.result);
		return l;



	}

}

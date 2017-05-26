package music;

import java.util.Scanner;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MusixLatte {
	public static void main(String[] args) throws UnirestException {
		
		//Enter artist and song to get lyrics 
		System.out.println("Enter an Artist and song to get lyrics"); //ask user for artist
		System.out.println("Enter an Artist (in lowercase)"); //ask user for artist
		Scanner scan = new Scanner(System.in); //scan input
		String artist = scan.nextLine(); //set input = to artist
		System.out.println("Enter a song by that artist (in lowercase)"); //ask user for song
		String song = scan.nextLine(); //set input = to song	
		
		MusixLatte o = MusixLatte.fetch(artist, song);
		
		System.out.println(o.lyrics);
		
	}
	
	//creates object
	String lyrics;
	String artist;
	String song;
	
	static MusixLatte fetch(String artist, String song) {
		
		//does the modifying stuff to artist and song
		artist = artist.toLowerCase();//goes to lowercase	
		artist = artist.replaceAll("\\s","");//gets rid of white spaces
		song = song.toLowerCase();//goes lower case 
		song = song.replaceAll("\\s","");//gets rid of white spaces	


		//put in api from website and enter singer/song
		HttpResponse<JsonNode> response;
		try {
			response = Unirest.get("https://musixmatchcom-musixmatch.p.mashape"
					+ ".com/wsr/1.1/matcher.lyrics.get?q_artist=" + artist+ "&q_track="+song) //artist and song entered into api
					.header("X-Mashape-Key", "2lhgIiI0MOmshMhpe8kK6k4Yqn9vp1Rmflpjsnnz9DKNX2B3gT")//api key
					.header("Accept", "application/json") 
					.asJson();
		
		
		//gets response, gets object, gets the lyrics from lyrics_body
			MusixLatte o = new MusixLatte();
			o.artist = artist;
			o.lyrics = response.getBody().getObject().get("lyrics_body").toString();
			o.song = song;
			return o; //returns lyrics
		
		
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null; //if nothing
		
	}
}

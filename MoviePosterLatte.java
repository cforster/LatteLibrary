package movie;
import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviePosterLatte {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}


	public static void main(String[] args) throws IOException, JSONException {
//		//fetch a random movie
		/*String query = fetch().replaceAll(" ", "+");
		System.out.println(fetch(query));*/
		
		//fetch user's specific search
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a movie to see its poster");
		String query = scan.nextLine();
		query = query.replaceAll(" ", "+");
		System.out.println(fetch(query));
	}

	/**
	 * get a random movie
	 * @return
	 * @throws FileNotFoundException
	 */
	static String fetch() throws FileNotFoundException{
		//import long list of movies
		Scanner scan = new Scanner(new BufferedReader(new FileReader("/Users/student/Desktop/movieList.txt")));
		ArrayList<String> titles = new ArrayList<String>();

		//create list of movies 
		while(scan.hasNext()){
			titles.add(scan.nextLine());
		}
		
		Random ran = new Random();
		int movie = ran.nextInt(titles.size() -1);
		
		return titles.get(movie);
	}

	/**
	 * get a specific movie user is searching for
	 * @param query
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	static String fetch(String query) throws JSONException, IOException{
		//prefix of url to poster
		String imageKey = "https://image.tmdb.org/t/p/w500/";
		//api with key
		//take in movie user is searching for
		//get to api
		String apiSearch = "https://api.themoviedb.org/3/search/movie?api_key=6d4b1c0570a1a091d8e50c57ca6a26c1&query=";
		//JSON objects returned
		JSONObject jsonSearch = readJsonFromUrl(apiSearch+query);

		//break up results of search
		JSONArray results = jsonSearch.getJSONArray("results");
		JSONObject data = results.getJSONObject(0);
		String [] stuff = data.toString().split(",\"");


		String title = stuff[2].replaceAll("original_title\":", "");
		title = title.replaceAll("\"", "");

		String posterPath = stuff[6].replaceAll("poster_path\":", "");
		posterPath = posterPath.replaceAll("\"", "");

		//combine image key and poster path
		StringBuilder sb = new StringBuilder();
		sb.append(imageKey);
		sb.append(posterPath);

		//print image in a new window
		URL pp = new URL(sb.toString());
		Image poster = ImageIO.read(pp);
		ImageIcon icon = new ImageIcon(poster);
		JLabel label = new JLabel();
		label.setIcon(icon);
		JFrame frame = new JFrame();
		frame.getContentPane().add(label);
		frame.setVisible(true);	
		frame.pack();

		return title;
	}
}



package bikelatte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONArray;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class BikeLatte {
	public static void main(String[] args) throws MalformedURLException, IOException, UnirestException {
		HttpResponse<JsonNode> response = Unirest.get("http://api.citybik.es/v2/networks")
				.asJson();
	
		JSONArray arr = response.getBody().getObject().getJSONArray("networks");
		int size = arr.length();
		Random gen = new Random();
		String city = "";
		String holder = "";
		Scanner scan = new Scanner(System.in);
		city = scan.nextLine();
		List<String> works = new ArrayList<String>();
		List<Location> locations = new ArrayList<Location>();
		
		for (int i = 0; i < size; i++) {
			Location test = new Location();
			
			holder = arr.getJSONObject(i).getJSONObject("location").get("city") + "";
			//System.out.println(holder);
			if(holder.toLowerCase().contains(city.toLowerCase())){
				test.setCity(arr.getJSONObject(i).getJSONObject("location").get("city") + "");
				test.setCountry(arr.getJSONObject(i).getJSONObject("location").get("country") + "");
				test.setLatitude(arr.getJSONObject(i).getJSONObject("location").get("latitude") + "");
				test.setLongitude(arr.getJSONObject(i).getJSONObject("location").get("longitude") + "");
				locations.add(test);
			}
		}
		if(locations.size() == 0)
		{
			System.out.println("You either spelled the city wrong, or that city doesn't have a citi bike. Try again");
		}
		for(int i = 0; i < locations.size(); i++)
		{
			System.out.println("The latitude of the city bike stand is " + locations.get(i).getLatitude());
			System.out.println("The longitude of the city bike stand is " +locations.get(i).getLongitude());
			System.out.println(locations.get(i).getCountry() + ", " + locations.get(i).getCity());
			
		}
	}
}
class Location {
	public String latitude;
	public String longitude;
	public String country;
	public String city;
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}

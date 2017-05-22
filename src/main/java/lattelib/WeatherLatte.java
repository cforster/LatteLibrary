import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import javax.print.StreamPrintService;

import com.google.gson.*;
import org.json.*;

public class WeatherLatte {
	private static final String PATH = "https://www.metaweather.com/api/location/";
	private static Feedback[] data;
	
	private static Feedback[] parseData(String json) {
		Gson gson = new Gson();
		Feedback[] f = gson.fromJson(json, Feedback[].class);
		return f;
	}
	
	
	public WeatherLatte() {
	}
	
	public void setData(Feedback[] data) {
		this.data = data;
	}
	
	public static WeatherLatte fetch(String location) {
		Document doc = null;
		
		try {
			doc = Jsoup.connect(
			       PATH + "search/?query=" + URLEncoder.encode(location, "ISO-8859-1")).ignoreContentType(true) 
			        .get();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		Feedback[] dat = parseData(doc.text()); 
		WeatherLatte mirror = new WeatherLatte(); // not using autovalue
		mirror.setData(dat);
		for(Feedback f : mirror.data) {
			f.pullWeather();
		}
		return mirror;
	}
	
	public String[] getNames() {
		String[] names = new String[data.length];
		for(int i = 0; i < data.length; i++) {
			names[i] = data[i].getTitle();
		}
		return names;
	}
	
	public String[] getTemps() {
		String[] names = new String[data.length];
		for(int i = 0; i < data.length; i++) {
			names[i] = Integer.toString(data[i].getThe_temp());
		}
		return names;
	}
	public String[] getHighs() {
		String[] names = new String[data.length];
		for(int i = 0; i < data.length; i++) {
			names[i] = Integer.toString(data[i].getMax_temp());
		}
		return names;
	}
	public String[] getLows() {
		String[] names = new String[data.length];
		for(int i = 0; i < data.length; i++) {
			names[i] = Integer.toString(data[i].getMin_temp());
		}
		return names;
	}
}

class Feedback {
	private static final String PATH = "https://www.metaweather.com/api/location/";
	private String title, location_type, woeid, latt_long;
	private String weather_state_name, applicable_date;
	private int min_temp, max_temp, the_temp;
	
	public Feedback(){
		
	}
	
	public void pullWeather(){
		Document doc = null;
		try {
			doc = Jsoup.connect(
			       PATH + URLEncoder.encode(woeid, "ISO-8859-1")).ignoreContentType(true) 
			        .get();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(doc.text());
		
		
		JSONObject obj = new JSONObject(doc.text());
		JSONArray arr = obj.getJSONArray("consolidated_weather");
		// arr[0] is the current day
		weather_state_name = arr.getJSONObject(0).getString("weather_state_name");
		applicable_date = arr.getJSONObject(0).getString("applicable_date");
		min_temp = arr.getJSONObject(0).getInt("min_temp");
		max_temp = arr.getJSONObject(0).getInt("max_temp");
		the_temp = arr.getJSONObject(0).getInt("the_temp");
		
	}
	

	public String getWeather_state_name() {
		return weather_state_name;
	}

	public String getApplicable_date() {
		return applicable_date;
	}

	public int getMin_temp() {
		return min_temp;
	}

	public int getMax_temp() {
		return max_temp;
	}

	public int getThe_temp() {
		return the_temp;
	}

	public String getTitle() {
		return title;
	}
	
	public String getLocation_type() {
		return location_type;
	}
	public String getWoeid() {
		return woeid;
	}
	public String getLatt_long() {
		return latt_long;
	}
	
}

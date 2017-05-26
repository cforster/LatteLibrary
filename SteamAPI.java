//imports:
import java.util.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//General information:
//Steam Web API Key: 73D37C386063A0DBC7B23C253500916F
//All API calls take the form http://api.steampowered.com/<interface name>/<method name>/v<version>/?key=<api key>&format=<format>.
//For information on the API methods: https://developer.valvesoftware.com/wiki/Steam_Web_API
public class SteamAPI
{ //start class

	public static void main(String[] args) throws IOException, URISyntaxException
	{ //start main
		
		Scanner scan = new Scanner(System.in); //declaring scanner

		//instructions
		System.out.println("You know what to do... write in the interface, method, and version, all separated by spaces.");
		System.out.println("Sample input: ISteamUserStats GetPlayerAchievements v0001");
		String user = scan.nextLine(); //take user input
		
		String[] compare = user.split(" ", 3); //spliting the input
		
		//declaring string variables (setting split inputs)
		String intrface = compare[0]; //apparently "interface" is it's own method so i can't use it..?
		String method = compare[1];
		String version = compare[2];
		
		//CHECK WHICH INTERFACE IT'S GOING THROUGH IN ORDER TO CHANGE THE URL
		if(intrface.equals("ISteamNews")) //GetNewsForApp
		{ //start if
			if(Desktop.isDesktopSupported()) //v0002
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?appid=440&count=3&maxlength=300&format=json"));
			}
		} //end if
		
		if(intrface.equals("ISteamUserStats"))
		{ //start if
			//opening the website:
			if(Desktop.isDesktopSupported() && method.equals("GetPlayerAchievements")) //v0001
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?appid=440&key=73D37C386063A0DBC7B23C253500916F&steamid=76561198079451544"));
			}
			else if(Desktop.isDesktopSupported() && method.equals("GetGlobalAchievementPercentagesForApp")) //v0002
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?gameid=440&format=json"));
			}
			else if(Desktop.isDesktopSupported() && method.equals("GetGlobalStatsForGame")) //v0001
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?format=xml&appid=17740&count=1&name[0]=global.map.emp_isle"));
			}
			else if(Desktop.isDesktopSupported() && method.equals("GetUserStatsForGame")) //v0002
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?appid=440&key=73D37C386063A0DBC7B23C253500916F&steamid=76561198079451544"));
			}
			else if(Desktop.isDesktopSupported() && method.equals("GetSchemaForGame")) //v2
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?key=73D37C386063A0DBC7B23C253500916F&appid=218620"));
			}
		} //end if
		
		if(intrface.equals("ISteamUser"))
		{ //start if
			if(Desktop.isDesktopSupported() && method.equals("GetFriendList")) //v0001
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?appid=440&key=73D37C386063A0DBC7B23C253500916F&steamid=76561198079451544&relationship=friend"));
			}
			else if(Desktop.isDesktopSupported() && method.equals("GetPlayerSummaries")) //v0002
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?key=73D37C386063A0DBC7B23C253500916F&steamids=76561198079451544"));
			} 
		} //end if

		if(intrface.equals("IPlayerService"))
		{ //start if
			if(Desktop.isDesktopSupported() && method.equals("GetOwnedGames")) //v0001
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?key=73D37C386063A0DBC7B23C253500916F&steamid=76561198079451544&format=json"));
			}
			else if(Desktop.isDesktopSupported() && method.equals("GetRecentlyPlayedGames")) //v0001
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?key=73D37C386063A0DBC7B23C253500916F&steamid=76561198079451544&format=json"));
			}
			else if(Desktop.isDesktopSupported() && method.equals("IsPlayingSharedGame")) //v0001
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?key=73D37C386063A0DBC7B23C253500916F&steamid=76561198079451544&appid_playing=240&format=json"));
			}
		} //end if
		
		if(intrface.equals("ITFItems_440"))
		{ //start if
			if(Desktop.isDesktopSupported())
			{
				Desktop.getDesktop().browse(new URI("http://api.steampowered.com/"+intrface+"/"+method+"/"+version+"/?appid=440&key=73D37C386063A0DBC7B23C253500916F&steamid=76561198079451544"));
			}
		} //end if


		
	} //end main

} //end class

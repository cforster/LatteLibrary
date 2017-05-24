import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StatLatte {
		String id;
		String name;
		String wins;
		String losses;
		String points_for;
		String points_against;
	
	static StatLatte fetch2016(String teamname)
	{
		StatLatte s = new StatLatte();
		try {
			Document doc = Jsoup.connect("http://api.sportradar.us/nfl-ot1/seasontd/2016/standings.xml?api_key=m7cnwfmxdfhgxyzfyheuwdr3").get();			
			for(int i = 0; i<32; i++)
			{
				Element link2 = doc.select("team").get(i);
				if(link2.attr("name").equals(teamname))
				{
				s.name = link2.attr("name");
				s.id = link2.attr("id");
				s.wins = link2.attr("wins");
				s.losses = link2.attr("losses");
				s.points_for = link2.attr("points_for");
				s.points_against = link2.attr("points_against");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	static StatLatte fetch2016()
	{
		StatLatte s = new StatLatte();
	    Random rand = new Random();
	    int num = rand.nextInt(32);
		try {
			Document doc = Jsoup.connect("http://api.sportradar.us/nfl-ot1/seasontd/2016/standings.xml?api_key=m7cnwfmxdfhgxyzfyheuwdr3").get();			
				Element link2 = doc.select("team").get(num);
				s.name = link2.attr("name");
				s.id = link2.attr("id");
				s.wins = link2.attr("wins");
				s.losses = link2.attr("losses");
				s.points_for = link2.attr("points_for");
				s.points_against = link2.attr("points_against");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	static StatLatte fetch2015(String teamname)
	{
		StatLatte s = new StatLatte();
		try {
			Document doc = Jsoup.connect("http://api.sportradar.us/nfl-ot1/seasontd/2015/standings.xml?api_key=m7cnwfmxdfhgxyzfyheuwdr3").get();			
			for(int i = 0; i<32; i++)
			{
				Element link2 = doc.select("team").get(i);
				if(link2.attr("name").equals(teamname))
				{
				s.name = link2.attr("name");
				s.id = link2.attr("id");
				s.wins = link2.attr("wins");
				s.losses = link2.attr("losses");
				s.points_for = link2.attr("points_for");
				s.points_against = link2.attr("points_against");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	static StatLatte fetch2015()
	{
		StatLatte s = new StatLatte();
	    Random rand = new Random();
	    int num = rand.nextInt(32);
		try {
			Document doc = Jsoup.connect("http://api.sportradar.us/nfl-ot1/seasontd/2015/standings.xml?api_key=m7cnwfmxdfhgxyzfyheuwdr3").get();			
				Element link2 = doc.select("team").get(num);
				s.name = link2.attr("name");
				s.id = link2.attr("id");
				s.wins = link2.attr("wins");
				s.losses = link2.attr("losses");
				s.points_for = link2.attr("points_for");
				s.points_against = link2.attr("points_against");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
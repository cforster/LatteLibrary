import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ChatLatte {
	private static final String PATH = "https://www.cleverbot.com/getreply";
	private static final String KEY = "CC2diW9tAnKHnzY7emUoqtqReeQ";
	private String chatID;

	private void setChatID(String cs) {
		this.chatID = cs;
	}
	
	public String submit(String s) {
		Document doc = null;

		try {
			doc = Jsoup.connect(PATH + "?" + "key=" + KEY + "&input=" + URLEncoder.encode(s, "ISO-8859-1") + "&cs=" + chatID + "&callback=ProcessReply").ignoreContentType(true).get();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		JSONObject obj = new JSONObject(doc.text().substring(13,doc.text().length()-2));
		
		return obj.getString("output");
	}

	public static ChatLatte fetch() {
		Document doc = null;

		try {
			doc = Jsoup.connect(PATH + "?" + "key=" + KEY).ignoreContentType(true).get();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		JSONObject obj = new JSONObject(doc.text());
		ChatLatte mirror = new ChatLatte();
		
		mirror.setChatID(obj.getString("cs"));
		
		return mirror;
	}
}

package lattelib;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by charlie on 8/7/16.
 */
public class StockLatte {
    public String name;
    public String symbol;
    public double lastprice;
    public double change;
    public double changepercent;
    public String timestamp;
    public long marketcap;
    public int volume;
    public double changeytd;
    public double changepercentytd;
    public double high;
    public double low;
    public double open;


    private static final String CACHESERVERADDR = "localhost";
    private static final String CACHESERVERPORT = "50123";

    //random:
    public StockLatte() {
        this(""); //TODO: pick from a big list
    }

    //given a ticker:
    public StockLatte(String symbol) {
        this.symbol = symbol;
        refresh();
    }

    //TODO: if the ticker doesn't exist (search fails), search for a ticker
    public void refresh() {
        try {
            URL u = new URL("http://" + CACHESERVERADDR +":" + CACHESERVERPORT + "/?l=stockquote&q="
                    + URLEncoder.encode(this.symbol, "ISO-8859-1"));
            JSONObject json = new JSONObject(new JSONTokener(new InputStreamReader(u.openStream())));
            json = json.getJSONObject("StockQuote");

            setValues(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setValues(JSONObject json) {
        this.name = json.getString("Name");
        this.symbol = json.getString("Symbol");
        this.lastprice = json.getDouble("LastPrice");
        this.change = json.getDouble("Change");
        this.changepercent = json.getDouble("ChangePercent");
        this.timestamp = json.getString("Timestamp");
        this.marketcap = json.getJSONObject("MarketCap").getLong("$numberLong");
        this.volume = json.getInt("Volume");
        this.changeytd = json.getDouble("ChangeYTD");
        this.changepercentytd = json.getDouble("ChangePercentYTD");
        this.high = json.getDouble("High");
        this.low = json.getDouble("Low");
        this.open = json.getDouble("Open");
    }
}

package lattelib;

import com.google.auto.value.AutoValue;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by charlie on 8/7/16.
 */
@AutoValue
public abstract class StockLatte {

    private static final String CACHESERVERADDR = "stocks.lattelib.com";
    private static final List<String> tickers = new ArrayList<>();

    @AutoValue.Builder
    public abstract static class Builder {
        abstract Builder name(String value);
        abstract Builder symbol(String value);
        abstract Builder lastprice(double value);
        abstract Builder change(double value);
        abstract Builder changepercent(double value);
        abstract Builder timestamp(String value);
        abstract Builder marketcap(long value);
        abstract Builder volume(int value);
        abstract Builder changeytd(double value);
        abstract Builder changepercentytd(double value);
        abstract Builder high(double value);
        abstract Builder low(double value);
        abstract Builder open(double value);
        abstract StockLatte build();
    }

    static {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            InputStream is = (InputStream) classLoader.getResource("data/tickers.txt").getContent();
            for (Scanner sc = new Scanner(is); sc.hasNext(); ) {
                tickers.add(sc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
            tickers.add("aapl");
        }
    }

    //get a random stock:
    public static StockLatte fetch() {
        StockLatte ret = null;
        Random gen = new Random();
        while(ret==null) {
            ret = fetch(tickers.get(gen.nextInt(tickers.size())));
        }
        return ret;
    }

    //given a ticker:
    public static StockLatte fetch(String symbol) {
        try {
            Document doc = Jsoup.connect(
                    "http://" + CACHESERVERADDR + "/MODApis/Api/v2/Quote?symbol="
                            + URLEncoder.encode(symbol, "ISO-8859-1"))
                    .get();

            if(!doc.select("status").text().equals("SUCCESS")) return null;

        return new AutoValue_StockLatte.Builder()
                .name(doc.select("Name").first().text())
                .symbol(doc.select("Symbol").first().text())
                .lastprice(Double.parseDouble(doc.select("LastPrice").first().text()))
                .change(Double.parseDouble(doc.select("Change").first().text()))
                .changepercent(Double.parseDouble(doc.select("ChangePercent").first().text()))
                .timestamp(doc.select("Timestamp").first().text())
                .marketcap(Long.parseLong(doc.select("MarketCap").first().text()))
                .volume(Integer.parseInt(doc.select("Volume").first().text()))
                .changeytd(Double.parseDouble(doc.select("ChangeYTD").first().text()))
                .changepercentytd(Double.parseDouble(doc.select("ChangePercentYTD").first().text()))
                .high(Double.parseDouble(doc.select("High").first().text()))
                .low(Double.parseDouble(doc.select("Low").first().text()))
                .open(Double.parseDouble(doc.select("Open").first().text()))
                .build();


        } catch(IOException e) {
            System.err.println("failed to get the stock latte!");
            e.printStackTrace();
            return null;
        }
    }

    public abstract String name();
    public abstract String symbol();
    public abstract double lastprice();
    public abstract double change();
    public abstract double changepercent();
    public abstract String timestamp();
    public abstract long marketcap();
    public abstract int volume();
    public abstract double changeytd();
    public abstract double changepercentytd();
    public abstract double high();
    public abstract double low();
    public abstract double open();
}

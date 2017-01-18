package lattelib;

import com.google.auto.value.AutoValue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by charlie on 8/8/16.
 */
@AutoValue
public abstract class WordLatte {
    private static final List<String> freq = new ArrayList<>();


    static {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = new File(classLoader.getResource("data/words.txt").getFile());
            for (Scanner sc = new Scanner(file); sc.hasNext(); ) {
                freq.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            freq.add("happy");
        }
    }

    @AutoValue.Builder
    public abstract static class Builder {
        abstract Builder partofspeech(String value);
        abstract Builder word(String value);
        abstract Builder definitions(List<String> value);
        abstract Builder examples(List<String> value);
        abstract Builder etymology(String value);
        abstract WordLatte build();
    }

    //get a random stock:
    public static WordLatte fetch() {
        WordLatte ret = null;
        Random gen = new Random();
        while(ret==null) {
            ret = fetch(freq.get(gen.nextInt(freq.size())));
        }
        return ret;
    }


    public static WordLatte fetch(String word) {
        try {
            Document doc = Jsoup.connect(
                    "http://dictionary.lattelib.com/api/v1/references/collegiate/xml/"
                            + URLEncoder.encode(word, "ISO-8859-1") + "?key=d421f47f-8854-4697-ba5b-13f8b7f36c06")
                    .ignoreContentType(true)
                    .get();

            Element e  = doc.select("entry").first();

            word = e.select("ew").text();

            List<String> defs = new ArrayList<>();
            Elements dels = e.select("def dt");
            for(int i = 0; i<dels.size(); i++) {
                if(dels.get(i)==null || dels.get(i).ownText().length()<3) continue;
                if(dels.get(i).ownText().contains(word)) continue;
                defs.add(dels.get(i).ownText().replaceAll(":", "").trim());
            }

            List<String> exs = new ArrayList<>();
            Elements eels = e.select("def dt vi");
            for(int i = 0; i<eels.size(); i++) {
                if(eels.get(i)!=null) exs.add(eels.get(i).text().replaceAll(word, "______"));
            }

            return new AutoValue_WordLatte.Builder()
                    .definitions(defs)
                    .examples(exs)
                    .etymology(e.select("et").text())
                    .partofspeech(doc.select("entry > fl").first().text())
                    .word(word)
                    .build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public abstract String partofspeech();
    public abstract String etymology();
    public abstract String word();
    abstract List<String> definitions();
    abstract List<String> examples();


    public int exampleCount() { return examples().size(); }
    public String example(int i) { return examples().get(i % exampleCount()); }
    public String example() { return examples().get(0); }
    public int definitionCount() { return definitions().size(); }
    public String definition(int i) { return definitions().get(i % definitionCount()); }
    public String definition() { return definition(0); }
}


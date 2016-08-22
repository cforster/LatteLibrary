package com.lattelib;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by charlie on 8/8/16.
 */
public class WordLatte {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("data/wordlist"));

        while(in.hasNextLine()) {
            new WordLatte(in.nextLine());
        }
    }

    private String partofspeech;
    private List<String> def = new ArrayList<String>();
    private List<String> ex = new ArrayList<String>();
    private String etymology = "";
    private String word;

    public String getPartOfSpeech() {
        return this.partofspeech;
    }
    public String getEtymology() {
        return this.etymology;
    }
    public String getWord() {
        return this.word;
    }
    public String getDefinition(int i) {
        return def.get(i % def.size());
    }
    public String getDefinition() {
        return getDefinition(0);
    }
    public String getExample(int i) {
        return ex.get(i % ex.size());
    }
    public int defCount() {
        return def.size();
    }
    public String getExample() {
        for(String e : ex) {
            if(e.length()>0) return e;
        }
        return "example not available";
    }
    public String toString() {
        StringBuilder o = new StringBuilder();
        o.append("word: " + getWord() + "\n");
        o.append("etym: " + getEtymology() + "\n");
        o.append("part: " + getPartOfSpeech() + "\n");
        for(int i =0; i<defCount(); i++) {
            o.append("def" + i + ": " + getDefinition(i) + "\n");
            o.append("exa" + i + ": " + getExample(i) + "\n");
        }
        return o.toString();
    }





    private static final String CACHESERVERADDR = "localhost";
    private static final String CACHESERVERPORT = "50123";


    //random:
    public WordLatte() {
        this(0.1, 0.5, 4, 8, "n");
    }

    public WordLatte(double startpercent, double endpercent, int minlength, int maxlength, String partofspeech) {
        //TODO: pick from a big list
    }

    //given a ticker:
    public WordLatte(String word) {
        this.word = word.toLowerCase();
        refresh();
    }

    //TODO: if the ticker doesn't exist (search fails), search for a ticker
    public void refresh() {
        try {
            URL u = new URL("http://" + CACHESERVERADDR +":" + CACHESERVERPORT + "/?l=dict&q="
                    + URLEncoder.encode(this.word, "ISO-8859-1"));
            JSONObject json = new JSONObject(new JSONTokener(new InputStreamReader(u.openStream())));

            try{
                setValues(json);
            } catch (JSONException e) {
                System.err.println("fail: " +json.toString());
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeExample(JSONObject o) {
        if(o==null) return "";
        JSONArray content = o.optJSONArray("content");
        if(content!=null) {
            String c0 = content.optString(0);
            String c1 = content.optString(1);
            if(c0==null || c1==null) return "";
            return c0 +
                    (c0.endsWith("-")?"":" ") +
                    "____" +
                    (c1.startsWith("-")?"":" ") +
                    c1;
        }
        else {
            //the word is at the end of the example:
            String example = o.optString("content");
            if(example == null) return "";
            if (!example.endsWith("-")) example += " ";
            example += "____";
            return example;
        }
    }

    private void setValues(JSONObject json) throws JSONException{
        //part of speech:
        this.partofspeech = json.getString("fl");

        //etymology:
        JSONObject et = json.optJSONObject("et");
        if(et!=null) {
            JSONArray arr = et.optJSONArray("content");
            if (arr == null) this.etymology = et.getString("content");
            else this.etymology = arr.getString(0);
        }

        //doesn't contain a def?!  me is an example of that.
        JSONObject def = json.optJSONObject("def");
        if(def==null) {
            this.def.add("");
            this.ex.add("");
            return;
        }

        JSONArray defs = json.getJSONObject("def").optJSONArray("dt");
        if(defs==null) {
            defs = new JSONArray("[" + json.getJSONObject("def").optJSONObject("dt") + "]");
        }
        for(int i =0; i<defs.length(); i++) {
            String definition = defs.optString(i).replaceAll(":","");
            String example = "";

            //if this is a full object:
            if(defs.optJSONObject(i)==null) {
                this.def.add(definition);
                this.ex.add(example);
                continue; //only contains the definition
            }

            JSONObject o = defs.optJSONObject(i);
            if(o==null) continue;

            //get the definition:
            definition = o.optString("content").replaceAll(":","").replaceAll(this.word, "____");
            if(definition == null) continue; //if the def is non existant
            if(definition.length()<=1) continue; //if the def is empty
            if(o.optJSONArray("content")!=null) continue; //if the def contains the word


            //if multiple examples:
            JSONArray ex = o.optJSONArray("vi");
            if(ex!=null) {
                for(int j = 0; j<ex.length(); j++) {
                    String it  = ex.getJSONObject(j).optString("it");
                    if(it==null || it.equals(this.word)) {
                        //the word is inside the example:
                        example = makeExample(ex.getJSONObject(j));
                        break;
                    }
                }
            }  else { //only one example:
                example = makeExample(o.optJSONObject("vi"));
            }

            this.def.add(definition);
            this.ex.add(example);
        }
    }
}

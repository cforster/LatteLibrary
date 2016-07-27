package com.lattelib;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by charlie on 7/21/16.
 */
public class DataLatte {
    public static String giphy(String tag) {
        try {
            String uri = "http://api.giphy.com/v1/gifs/random?tag=" + tag.trim().replaceAll(" ", "+") + "&api_key=dc6zaTOxFJmzC";

            URL giphy = new URL(uri);

            JSONObject g = new JSONObject(new JSONTokener(new InputStreamReader(giphy.openStream())));

            return g.getJSONObject("data").getString("image_url");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http://media0.giphy.com/media/7beSjLq7J6qfS/giphy.gif";
    }
}

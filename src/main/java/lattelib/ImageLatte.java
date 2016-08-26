package lattelib;

import com.google.auto.value.AutoValue;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by charlie on 8/9/16.
 */
@AutoValue
public abstract class ImageLatte {

    abstract String url();

    public static ImageLatte fetchGiphy(String ref) {
        try {
            String uri = "http://api.giphy.com/v1/gifs/random?tag=" + ref.trim().replaceAll(" ", "+") + "&api_key=dc6zaTOxFJmzC";

            URL giphy = new URL(uri);

            JSONObject g = new JSONObject(new JSONTokener(new InputStreamReader(giphy.openStream())));

            return new AutoValue_ImageLatte(g.getJSONObject("data").getString("image_url"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AutoValue_ImageLatte("http://media0.giphy.com/media/7beSjLq7J6qfS/giphy.gif");
    }

    public String toString() {
        return url();
    }
}

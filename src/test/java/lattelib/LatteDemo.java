package lattelib;

import java.awt.*;

/**
 * Created by charlie on 5/16/17.
 */
public class LatteDemo {
    public static void main(String[] args) {
        WebLatte frame = new WebLatte();

        ImageLatte i = ImageLatte.fetchGiphy("cat");

        frame.drawImage(i.url(), 100,100,500,500,0);
        frame.paint();

    }
}

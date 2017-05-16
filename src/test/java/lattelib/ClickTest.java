package lattelib;

import org.w3c.dom.Element;

/**
 * Created by charlie on 5/16/17.
 */
public class ClickTest {
    public static void main(String[] args) {
        WebLatte frame = new WebLatte();

        Element e = frame.drawCircle(100,100,75, ColorLatte.AliceBlue);
        e.setAttribute("class", "clickable");
        frame.addButton("charlie", 10, 20);
        frame.paint();

        while(true) {
            frame.println(frame.nextClick());
        }
    }

}

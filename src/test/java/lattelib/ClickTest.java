package lattelib;

import org.w3c.dom.Element;

/**
 * Created by charlie on 5/16/17.
 */
public class ClickTest {
    public static void main(String[] args) {
        WebLatte frame = new WebLatte();

        frame.addButton("mybutton", 200,200);
        Element e = frame.drawCircle(100, 100, 75, ColorLatte.AliceBlue);
        WebLatte.makeClickable(e, "mycircle");
        frame.paint();

        while(true) {
            frame.println(frame.nextClick());
        }
    }

}

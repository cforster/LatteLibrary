package lattelib;

import org.w3c.dom.Element;

/**
 * Created by charlie on 5/16/17.
 */
public class ClickTest {
    public static void main(String[] args) {
        WebLatte frame = new WebLatte();

        int i = 0;

//        while(true) {
            Element e = frame.drawCircle(100, 100+i, 75, ColorLatte.AliceBlue);
            WebLatte.makeClickable(e, "mycircle");
            Element e2 = frame.drawRectangle(200,200,75, 75, 0, ColorLatte.Gold);
            frame.paint();
//            i++;

//        }
        while(true) {
            frame.nextLine();
            frame.println(frame.nextClick());
        }
    }

}

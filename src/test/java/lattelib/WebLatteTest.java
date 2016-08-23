package lattelib;

/**
 * Created by charlie on 8/22/16.
 */
public class WebLatteTest {

    public static void main(String[] args) {
        WebLatte frame = new WebLatte();

//
//        frame.println("hello what's <b>your</b> name");
//        String s = frame.nextLine().toString();

        frame.clearConsole();

        int i =10;
        long t = System.currentTimeMillis();
        while(true) {
//            System.out.println(System.currentTimeMillis()-t);
            t = System.currentTimeMillis();
            frame.clearPaint();
            for (int j = 0; j < 100; j++) {

                frame.drawRectangle(100, (j*20)%300, 300, j+50, 0, Color.AliceBlue);
            }
            frame.drawText("hello ", 100, 240, 50, 0, Color.GoldenRod);
//            frame.drawText("x: " + frame.getLeapX() + ", " + "y: " + frame.getLeapY(), frame.getLeapX(),
//                    frame.getLeapY(), 20, 0, Color.Aqua);
            frame.paint();
            i++;
        }
    }
}

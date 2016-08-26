package lattelib;

/**
 * Created by charlie on 8/25/16.
 */
public class ConsoleTest {
    public static void main(String[] args) {
        WebLatte frame = new WebLatte();
        frame.println("whats your name?");
        String name = frame.nextLine().toString();
        frame.println("hello " +name +", pick a number");
        int number = frame.nextLine().toInt();
        frame.println("number: "  + number);
    }

}

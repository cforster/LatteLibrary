package lattelib;

/**
 * Created by charlie on 8/25/16.
 */
public class ImageTest {
    public static void main(String[] args) {
        ImageLatte l = ImageLatte.fetchGiphy("charlie");
        System.out.println(l);
    }
}

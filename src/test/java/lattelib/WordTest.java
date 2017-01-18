package lattelib;

/**
 * Created by charlie on 8/25/16.
 */
public class WordTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            WordLatte l = WordLatte.fetch();
            System.out.println(l);
        }
    }
}

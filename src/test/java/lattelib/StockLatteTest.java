package lattelib;

/**
 * Created by charlie on 8/25/16.
 */
public class StockLatteTest {
    public static void main(String[] args) {

        for (int i = 0; i < 50; i++) {
//        {
            StockLatte quote = StockLatte.fetch();
            System.out.println(quote);
        }
    }
}

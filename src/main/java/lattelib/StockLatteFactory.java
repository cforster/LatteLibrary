package lattelib;

/**
 * Created by charlie on 1/28/17.
 */
public enum StockLatteFactory {
    INSTANCE;

    public StockLatte fetch() {
        return StockLatte.fetch();
    }

    public StockLatte fetch(String symbol) {
        return StockLatte.fetch(symbol);
    }
}

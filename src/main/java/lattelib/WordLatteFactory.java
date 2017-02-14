package lattelib;

/**
 * Created by charlie on 1/28/17.
 */
public enum WordLatteFactory {
    INSTANCE;

    public WordLatte fetch() {
        return WordLatte.fetch();
    }

    public WordLatte fetch(String word) {
        return WordLatte.fetch(word);
    }
}

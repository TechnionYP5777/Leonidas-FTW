package utils;

/**
 * @athor RoeiRaz
 *
 * holds the strings that were added before and after the original sting to transform it into a compilation unit
 */
public class Wrap {
    private String before;
    private String after;

    public Wrap(String before, String after) {
        this.before = before;
        this.after = after;
    }

    /**
     * @param src - the original string
     * @return - the wrapped string, starts with before and ends with after
     */
    public String wrap(String src) {
        return this.before + src + this.after;
    }

    /**
     * @param src - the wrapped string, starts with before and ends with after
     * @return - the original string
     */
    public String unwrap(String src) {
        return src.substring(before.length(), src.length() - after.length());
    }
}

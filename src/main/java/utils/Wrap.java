package utils;

/**
 * @athor RoeiRaz
 */
public class Wrap {
    private String before;
    private String after;

    public Wrap(String before, String after) {
        this.before = before;
        this.after = after;
    }

    public String wrap(String src) {
        return this.before + src + this.after;
    }

    public String unwrap(String src) {
        return src.substring(before.length(), src.length() - after.length());
    }
}

package utils;

/**
 * Created by roei on 12/9/16.
 */
public class Wrap {
    protected String before;
    protected String after;
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

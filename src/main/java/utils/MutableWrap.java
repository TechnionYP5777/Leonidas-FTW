package utils;

/**
 * Created by roei on 12/9/16.
 */
public class MutableWrap extends Wrap {

    boolean locked = false;

    public MutableWrap() {
        super("", "");
    }

    public MutableWrap(String before, String after) {
        super(before, after);
    }

    @Override
    public String wrap(String src) {
        locked = true;
        return super.wrap(src);
    }

    public void set(String before, String after) {
        if(locked) throw new RuntimeException("Cant change a wrap after it was used to wrap a string!");
        this.before = before;
        this.after = after;
    }


}

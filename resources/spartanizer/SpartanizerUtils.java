package spartanizer;

import java.util.List;

public class SpartanizerUtils {
    public static <T> T unless(boolean x, T y) {
        return x ? null : y;
    }

    public static <T> T last(List<T> l) {
        return l.get(l.size() - 1);
    }
}
package spartanizer;

import java.util.List;

public class SpartanizerUtils {

    public class Unless {
        boolean x;

        private Unless(boolean x){
            this.x = x;
        }

        public <T> T eval(T y){
            return x ? null : y;
        }
    }

    public static Unless unless(boolean x) { return Unless(x); }

    public static <T> T last(List<T> l) {
        return l.get(l.size() - 1);
    }
}
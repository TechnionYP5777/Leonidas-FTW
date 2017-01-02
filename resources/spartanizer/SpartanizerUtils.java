package spartanizer;

import java.util.List;
import java.util.function.Function;

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

    public static <T,R> R nullConditional(T x, Function<T,R> func){return x != null ? func.apply(x) : null;}
}
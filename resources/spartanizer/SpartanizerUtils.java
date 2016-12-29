package spartanizer;

public class SpartanizerUtils {
    public static <T> T unless(boolean x, T y){
        return x ? null : y;
    }
}
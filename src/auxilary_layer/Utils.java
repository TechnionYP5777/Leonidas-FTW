package auxilary_layer;

import java.util.Arrays;

/**
 * @author Oren Afek
 * @since 2016.12.1
 */

public enum Utils {
    ;

    public static <T> boolean in(T candidate, T... list) {
        return Arrays.stream(list).anyMatch(elem -> elem.equals(candidate));
    }

}

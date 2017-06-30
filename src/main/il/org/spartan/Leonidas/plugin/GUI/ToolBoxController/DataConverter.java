package il.org.spartan.Leonidas.plugin.GUI.ToolBoxController;

import java.util.function.Function;

/**
 * Created by Sharon on 30-Jun-17.
 */
public class DataConverter<T> {
    public static DataConverter<String> STRING = new DataConverter<>(s -> s, s -> s);

    private Function<T, String> toString;
    private Function<String, T> fromString;

    public DataConverter(Function<T, String> toString, Function<String, T> fromString) {
        this.toString = toString;
        this.fromString = fromString;
    }

    public String toString(T t) {
        return toString.apply(t);
    }

    public Object fromString(String s) {
        return fromString.apply(s);
    }

    public static String convert(Object o) {
        if (o.getClass().isAssignableFrom(String.class)) {
            return STRING.toString((String) o);
        }

        return "";
    }

    public static Object decode(String s, Class<?> c) {
        if (c.isAssignableFrom(String.class)) {
            return STRING.fromString(s);
        }

        return "";
    }
}

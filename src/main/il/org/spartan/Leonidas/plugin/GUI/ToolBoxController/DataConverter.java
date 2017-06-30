package il.org.spartan.Leonidas.plugin.GUI.ToolBoxController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Sharon on 30-Jun-17.
 */
public class DataConverter<T> {
    public static DataConverter<String> STRING = new DataConverter<>(s -> s, s -> s);
    public static DataConverter<Integer> INTEGER = new DataConverter<>(i -> Integer.toString(i), Integer::parseInt);
    public static DataConverter<Double> DOUBLE = new DataConverter<>(i -> Double.toString(i), Double::parseDouble);
    public static DataConverter<Float> FLOAT = new DataConverter<>(i -> Float.toString(i), Float::parseFloat);
    public static DataConverter<Long> LONG = new DataConverter<>(i -> Long.toString(i), Long::parseLong);
    public static DataConverter<List<String>> STRING_LIST = new DataConverter<>(list -> String.join(",", list),
            s -> new ArrayList<>(Arrays.asList(s.split("\\s*,\\s*"))));

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
        } else if (o.getClass().isAssignableFrom(Integer.class)) {
            return INTEGER.toString((Integer) o);
        } else if (o.getClass().isAssignableFrom(Integer.class)) {
            return DOUBLE.toString((Double) o);
        } else if (o.getClass().isAssignableFrom(Integer.class)) {
            return FLOAT.toString((Float) o);
        } else if (o.getClass().isAssignableFrom(Integer.class)) {
            return LONG.toString((Long) o);
        } else if (o.getClass().isAssignableFrom(List.class)) {
            return STRING_LIST.toString((List<String>) o);
        }

        return "";
    }

    public static Object decode(String s, Class<?> c) {
        if (c.isAssignableFrom(String.class)) {
            return STRING.fromString(s);
        } else if (c.isAssignableFrom(Integer.class)) {
            return INTEGER.fromString(s);
        } else if (c.isAssignableFrom(Double.class)) {
            return DOUBLE.fromString(s);
        } else if (c.isAssignableFrom(Float.class)) {
            return FLOAT.fromString(s);
        } else if (c.isAssignableFrom(Long.class)) {
            return LONG.fromString(s);
        } else if (c.isAssignableFrom(List.class)) {
            return STRING_LIST.fromString(s);
        }

        return "";
    }
}

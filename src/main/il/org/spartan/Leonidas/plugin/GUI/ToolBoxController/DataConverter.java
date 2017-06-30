package il.org.spartan.Leonidas.plugin.GUI.ToolBoxController;

import com.intellij.util.Function;

/**
 * Created by Sharon on 30-Jun-17.
 */
public class DataConverter<T> {
    private Function<T, String> toString;
    private Function<String, T> fromString;

    public static String toString(Object obj) {
        return ""; // TODO
    }

    public static Object fromString(String s) {
        return new Object(); // TODO
    }
}

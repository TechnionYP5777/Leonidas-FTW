package il.org.spartan.ispartanizer.plugin.utils.logging;

import org.jetbrains.annotations.Nullable;

/**
 * [not finished]
 * Container for single entry of the BasicLogger log.
 *
 * @author RoeiRaz
 * @since 31-3-17
 */
public class BasicLogEntry {
    Class clazz;
    Throwable e;
    private int date;
    private String msg;

    public BasicLogEntry(int date, Class<?> clazz, String msg) {

    }

    public BasicLogEntry(int date, Class<?> clazz, String msg, @Nullable Throwable e) {

    }

    public static BasicLogEntry[] fromString(String log) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}

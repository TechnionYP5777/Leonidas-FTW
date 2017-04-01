package il.org.spartan.ispartanizer.plugin.utils.logging;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * [not finished]
 * Container for single entry of the Logger log.
 *
 * @author RoeiRaz
 * @since 31-3-17
 */
public class BasicLogEntry {

    static final private String WEAK_SEPERATOR = "  ";
    static final private String DATE_FORMAT = "HH:mm:ss  dd/MM";
    static final private String LOG_ENTRY_TRAILER = "\n";

    private Logger.LEVEL logLevel;
    private Class clazz;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})    // Currently this field isn't used.
    // TODO @RoeiRaz remove this after the throwable is being used.
    private Throwable e;
    private Date date;
    private String msg;

    BasicLogEntry(Logger.LEVEL logLevel, Class<?> clazz, String msg) {
        this(logLevel, clazz, msg, null);
    }

    BasicLogEntry(Logger.LEVEL logLevel, Class<?> clazz, String msg, @Nullable Throwable e) {
        this(new Date(), logLevel, clazz, msg, e);
    }

    private BasicLogEntry(Date date, Logger.LEVEL logLevel, Class<?> clazz, String msg, @Nullable Throwable e) {
        this.date = date;
        this.logLevel = logLevel;
        this.clazz = clazz;
        this.msg = msg;
        this.e = e;
    }

    Logger.LEVEL getLogLevel() {
        return logLevel;
    }

    private String getFormatedDate() {
        return new SimpleDateFormat(DATE_FORMAT).format(this.date);
    }

    @Override
    public String toString() {
        return getFormatedDate() + WEAK_SEPERATOR + logLevel + WEAK_SEPERATOR + clazz.getSimpleName() + WEAK_SEPERATOR
                + msg.replace("\n", "").replace("\r", "") + LOG_ENTRY_TRAILER;
    }
}

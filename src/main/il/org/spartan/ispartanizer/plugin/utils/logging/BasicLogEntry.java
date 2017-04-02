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


    static final private String SPACE = "  ";
    static final private String DATE_FORMAT = "HH:mm:ss  dd/MM";
    static final private String LOG_ENTRY_TRAILER = "\n";
    static final private String LOG_MSG_LEADER = ">    ";

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

    private String getFormatedMsg() {
        return LOG_MSG_LEADER
                + this.msg.replace("\r\n", "\r\n" + LOG_MSG_LEADER)
                .replace("\n", "\n" + LOG_MSG_LEADER);
    }

    @Override
    public String toString() {
        return getFormatedDate() + SPACE + logLevel + SPACE + clazz.getName() + SPACE + ":" + "\n"
                + getFormatedMsg() + LOG_ENTRY_TRAILER;
    }
}

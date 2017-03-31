package il.org.spartan.ispartanizer.plugin.utils.logging;

/**
 * Logging interface.
 *
 * @author RoeiRaz
 * @since 31-3-17
 */
public interface Logger {

    void debug(String msg);

    void info(String msg);

    void warn(String msg);

    void warn(String msg, Throwable t);

    void error(String msg);

    void error(String msg, Throwable t);

    enum LEVEL {
        DEBUG(0),
        INFO(1),
        WARN(2),
        ERROR(3);

        private final int level;

        LEVEL(int level) {
            this.level = level;
        }

        int getLevel() {
            return level;
        }
    }

}
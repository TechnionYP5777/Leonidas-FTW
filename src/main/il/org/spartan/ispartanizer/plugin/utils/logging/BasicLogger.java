package il.org.spartan.ispartanizer.plugin.utils.logging;

/**
 * Basic Logger implementation, writes to a single log file.
 *
 * @author RoeiRaz
 * @since 31-3-17
 */
class BasicLogger implements Logger {

    @Override
    public void debug(String msg) {

    }

    @Override
    public void info(String msg) {

    }

    @Override
    public void warn(String msg) {
        LogFileUtils.appendToLogFile(msg);
    }

    @Override
    public void warn(String msg, Throwable t) {

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void error(String msg, Throwable t) {

    }
}

package il.org.spartan.ispartanizer.plugin.utils.logging;

/**
 * Generates Logger instances.
 *
 * @author RoeiRaz
 * @since 31-3-17
 */
public class LoggerFactory {
    /**
     * Currently returns null because the logging tool is not yet intended for use.
     *
     * @param clz - The class in which the logger will be used.
     * @return returns a logger specialized for the given class.
     * TODO @RoeiRaz fix the method after the logging tool is usable
     */
    public static Logger createLogger(Class<?> clz) {
        return null;
        //return new BasicLogger();
    }
}

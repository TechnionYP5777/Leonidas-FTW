package il.org.spartan.ispartanizer.plugin.leonidas;

/**
 * @author AnnaBel7
 * @since 06/01/2017.
 */

/**
 * a descriptor of the amount of statements in block
 */
public enum Amount {
    ANY,
    EMPTY,
    EXACTLY_ONE,
    AT_LEAST_ONE;

    public boolean conforms(int x) {
        switch (this) {
            case ANY:
                return true;
            case EMPTY:
                return x == 0;
            case EXACTLY_ONE:
                return x == 1;
            case AT_LEAST_ONE:
                return x >= 1;
            default:
                return false;
        }
    }

    public boolean notConforms(int x) {
        return !conforms(x);
    }
}

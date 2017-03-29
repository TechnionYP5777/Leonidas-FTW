package il.org.spartan.ispartanizer.plugin.tippers.leonidas;

import java.util.function.Supplier;

/**
 * @author Sharon Kuninin, Oren Afek
 * @since 26-03-2017.
 */
public interface LeonidasTipperDefinition {
    /**
     * @return
     */
    Template matcher();

    Template replacer();

    void constraints();

    /**
     * Created by on 3/27/2017.
     */
    @FunctionalInterface
    interface Matcher {
        void template();
    }

    /**
     * @author Sharon Kuninin, michalcohen
     * @since 26-03-2017.
     */
    interface Replacer {
        void template();
    }

    class Template {
        public Template(Runnable __) {/**/}

        public Template(Supplier<?> __) {/**/}
    }
}

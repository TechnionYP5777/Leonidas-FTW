package il.org.spartan.ispartanizer.plugin.tippers.leonidas;

import java.util.function.Supplier;

/**
 * @author Oren Afek
 * @since 3/29/2017.
 */
public interface LeonidasTemplate {



    @FunctionalInterface
    interface Func {
        void a();
    }

    void from();

    void to();
}

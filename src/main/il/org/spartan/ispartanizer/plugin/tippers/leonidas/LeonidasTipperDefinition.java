package il.org.spartan.ispartanizer.plugin.tippers.leonidas;

import com.intellij.psi.PsiElement;

import java.util.function.Supplier;

/**
 * @author Sharon Kuninin, Oren Afek
 * @since 26-03-2017.
 */
public interface LeonidasTipperDefinition {
    /**
     * @return
     */

    default Matcher matcherBuilder() {
        return matcher();
    }

    Matcher matcher();

    Replacer replacer();

    void constraints();

    default <T> void is(T t, Supplier<T> template, Class<? extends PsiElement> root) {/**/}

    default <T> void is(T t, Supplier<T> template) {/**/}

    default <T> void isNot(T t, Supplier<T> template, Class<? extends PsiElement> root) {/**/}

    default <T> void isNot(T t, Supplier<T> template) {/**/}

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
}

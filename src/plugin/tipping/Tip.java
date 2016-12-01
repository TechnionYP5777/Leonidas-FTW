package plugin.tipping;


import com.intellij.psi.PsiElementFactory;

/**
 * @author Oren Afek
 * @author Michal Cohen
 * @since 2016.12.1
 */

@FunctionalInterface
public interface Tip {
    void go(PsiElementFactory elementFactory);
}

package plugin.tipping;


import auxilary_layer.PsiRewrite;
import com.intellij.psi.PsiElementFactory;

/**
 * @author Oren Afek
 * @author Michal Cohen
 * @since 2016.12.1
 */

@FunctionalInterface
public interface Tip {
    void go(PsiRewrite r);
}

package plugin.tipping;


import auxilary_layer.PsiRewrite;

/**
 * @author Oren Afek
 * @author Michal Cohen
 * @since 2016.12.1
 */

@FunctionalInterface
public interface Tip {
    void go(PsiRewrite r);
}

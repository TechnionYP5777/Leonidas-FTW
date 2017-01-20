package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;
import il.org.spartan.ispartanizer.plugin.tipping.Tipper;

/**
 * @author melanyc
 * @since 20-01-2017.
 */
public class NoTip<T extends PsiElement> implements Tipper<T> {
    @Override
    public boolean canTip(PsiElement e) {
        return true;
    }

    @Override
    public String description(PsiElement element) {
        return "";
    }

    @Override
    public Tip tip(T node) {
        return new Tip("", node, NoTip.class) {
            @Override
            public void go(PsiRewrite r) {
            }
        };
    }

    @Override
    public Class<? extends T> getPsiClass() {
        return null;
    }
}

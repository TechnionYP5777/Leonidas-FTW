package plugin.tippers;

import com.intellij.psi.PsiElement;
import plugin.tipping.Tipper;
import plugin.tipping.Tip;
import plugin.tipping.TipperCategory;
import auxilary_layer.PsiRewrite;

import java.util.Collection;

/**
 * Created by maorroey on 12/26/2016.
 */
public abstract class NanoPatternTipper<N extends PsiElement> implements Tipper<N>, TipperCategory.Nanos{
    protected static <N extends PsiElement> boolean anyTips(final Collection<Tipper<N>> ns, final N n) {
        return n!=null && ns.stream().anyMatch(t -> t.canTip(n));
    }

    protected static <N extends PsiElement> Tipper<N> firstTipperThatCanTip(final Collection<Tipper<N>> ns, final N n){
        return ns.stream().filter(t -> t.canTip(n)).findFirst().get();
    }

    public static <N extends PsiElement> Tip firstTip(final Collection<Tipper<N>> ns, final N n) {
        return firstTipperThatCanTip(ns, n).tip(n);
    }

    @Override public final Tip tip(final N ¢) {
        return new Tip(description(¢), ¢, this.getClass()) {
            @Override public void go(final PsiRewrite r) {
                pattern(¢).go(r);
            }
        };
    }

    String className() {
        return this.getClass().getSimpleName();
    }

    protected abstract Tip pattern(final N ¢);

}

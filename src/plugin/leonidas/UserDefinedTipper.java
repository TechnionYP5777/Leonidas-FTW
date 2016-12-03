package plugin.leonidas;

/**
 * Created by maorroey on 12/3/2016.
 */

import com.intellij.psi.*;
import plugin.tipping.*;


public abstract class UserDefinedTipper<N extends PsiElement> implements Tipper<N>, TipperCategory.Nanos {
    @Override public final boolean canTip(final N ¢) {
        return prerequisite(¢);
    }

    /** @param ¢ the PSIElement being inspected.
     * @return <code><b>true</b></code> <i>iff</i> the argument holds all the
     *         conditions needed for a tip to be possible. */
    protected abstract boolean prerequisite(final N ¢);

    /** @param n the PsiElement to be inspected.
     * @param s the pattern matching to be found in the PsiElement (for example $X1).
     * @return the PsiElement representing s. */
    public abstract PsiElement getMatching(PsiElement n, String s);
}
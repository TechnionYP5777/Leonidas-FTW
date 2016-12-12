package plugin.leonidas;

/**
 * @Author Roey Maor
 * @since 3/12/2016.
 */


import com.intellij.psi.PsiElement;
import plugin.tipping.Tipper;
import plugin.tipping.TipperCategory;


public abstract class UserDefinedTipper<N extends PsiElement> implements Tipper<N>, TipperCategory.Nanos {


    /** @param ¢ the PSIElement being inspected.
     * @return <code><b>true</b></code> <i>iff</i> the argument holds all the
     *         conditions needed for a tip to be possible. */
    protected abstract boolean prerequisite(final N ¢);

    /** @param e the PsiElement to be inspected.
     * @param s the pattern matching to be found in the PsiElement (for example $X1).
     * @return the PsiElement representing s. */
    public abstract PsiElement getMatching(PsiElement e, String s);
}
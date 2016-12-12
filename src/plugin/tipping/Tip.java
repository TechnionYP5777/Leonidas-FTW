package plugin.tipping;


import auxilary_layer.PsiRewrite;
import auxilary_layer.PsiUtils;
import com.intellij.psi.PsiElement;

/**
 *
 * @author Roey Maor
 * @since 2016.12.1
 */

/*I took the idea of extending range from the original spartanizer,
 * in the original project, the range represents the character indexes that
 * mark the area where the tip is relevant.
 * this inherently means that a tip is relevant only to a continues block of code
 */

public abstract class Tip extends Range {
    /** A textual description of the action to be performed **/
    public final String description;
    /** The tipper class that supplied that tip */
    @SuppressWarnings("rawtypes") public final Class<? extends Tipper> tipperClass;
    /**
     * The line number of the first character to be rewritten
     **/
    public int lineNumber = -1;

    /** Instantiates this class
     * @param description a textual description of the changes described by this
     *        instance
     * @param n the node on which change is to be carried out
     * @param ns additional nodes, defining the scope of this action. */
    public Tip(final String description, final PsiElement n, @SuppressWarnings("rawtypes") final Class<? extends Tipper> tipperClass,
               final PsiElement... ns) {
        this(description, range(n, ns), tipperClass);
        lineNumber = PsiUtils.getDocumentFromPsiElement(n).getLineNumber(n.getTextOffset());
    }

    Tip(final String description, final Range other, @SuppressWarnings("rawtypes") final Class<? extends Tipper> tipperClass) {
        super(other);
        this.description = description;
        this.tipperClass = tipperClass;
    }

    /**
     * A factory function that converts a sequence of PSIElements into a
     * {@link Range}
     *
     * @param e  arbitrary
     * @param es
     */
    static Range range(final PsiElement e, final PsiElement... es) {
        return range(singleNodeRange(e), es);
    }

    static Range range(final Range r, final PsiElement... es) {
        Range $ = r;
        for (final PsiElement ¢ : es)
            $ = $.merge(singleNodeRange(¢));
        return $;
    }

    static Range singleNodeRange(final PsiElement ¢) {
        final int $ = ¢.getTextOffset();
        return new Range($, $ + ¢.getTextLength());
    }

    /** Convert the rewrite into changes on an {@link PsiRewrite}
     * @param r where to place the changes
     */
    public abstract void go(PsiRewrite r);

}
package plugin.leonidas;

import com.intellij.psi.PsiBlockStatement;
import com.intellij.psi.PsiElement;
import plugin.tipping.Tip;
import auxilary_layer.*;

/**
 * @Author Roey Maor
 * @since 2/12/2016.
 */

public class TipperFactory {
    public static UserDefinedTipper<PsiBlockStatement> statementsPattern(final String _pattern, final String _replacement, final String description) {
        return newSubBlockTipper(_pattern, _replacement, description);
    }

    private static UserDefinedTipper<PsiBlockStatement> newSubBlockTipper(final String pattern, final String replacement, final String description) {
        final Matcher m = new Matcher(pattern, replacement);
        return new UserDefinedTipper<PsiBlockStatement>() {
            @Override public Tip tip(final PsiBlockStatement n) {
                /*
                return new Tip(description(n), n, this.getClass(), m.getMatchedNodes(az.block(n))) {
                    @Override public void go(final PsiRewrite r){ //final TextEditGroup g) {
                      //  final PsiElement psiE = m.blockReplacement(n);
                       // r.replace(n, psiE); // ,g);
                    }
                };*/

                return null;
            }

            @Override
            public Class<PsiBlockStatement> getPsiClass() {
                return PsiBlockStatement.class;
            }

            @Override
            public boolean canTip(PsiElement e) {
                return e instanceof PsiBlockStatement && prerequisite((PsiBlockStatement) e);
            }

            @Override protected boolean prerequisite(final PsiBlockStatement ¢) {
                return m.blockMatches(¢);
            }

            @Override public String description(@SuppressWarnings("unused") final PsiBlockStatement __) {
                return description;
            }

            @Override public PsiElement getMatching(final PsiElement n, final String s) {
                return m.getMatching(n, s);
            }
        };
    }

    public static <N extends PsiElement> UserDefinedTipper<N> patternTipper(final String pattern, final String replacement) {
        return patternTipper(pattern, replacement, String.format("[%s] => [%s]", pattern, replacement));
    }

    /** Creates a tipper that can tip ASTNodes that can be matched against
     * <b>_pattern</b>,<br>
     * and transforms them to match the pattern <b>_replacement</b>, using the
     * same values<br>
     * for each pattern variable.
     * @param _pattern Pattern to match against
     * @param _replacement Replacement pattern
     * @param description Description of the tipper
     * @return {@link UserDefinedTipper} */
    public static <N extends PsiElement> UserDefinedTipper<N> patternTipper(final String _pattern, final String _replacement, final String description) {
        final Matcher m = new Matcher(_pattern, _replacement);
        return new UserDefinedTipper<N>() {
            @Override
            public boolean canTip(PsiElement e) {
                return false;
            }

            @Override public String description(@SuppressWarnings("unused") final N __) {
                return description;
            }

            @Override public Tip tip(final N n) {
                /*
                return new Tip(description(n), n, this.getClass()) {
                    @Override public void go(final PsiRewrite r){
                        r.replace(n, m.replacement(n));
                    }
                };*/

                return null;
            }

            @Override
            public Class<N> getPsiClass() {
                return null;
            }

            @Override protected boolean prerequisite(final N ¢) {
                return m.matches(¢);
            }

            @Override public PsiElement getMatching(final PsiElement n, final String s) {
                return m.getMatching(n, s);
            }
        };
    }
}

package plugin.leonidas;

import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiBlockStatement;
import com.intellij.psi.PsiElement;
import auxilary_layer.iz;
import auxilary_layer.az;
import com.intellij.psi.PsiStatement;
import java.util.*;

/**
 * Created by maorroey on 12/2/2016.
 */

public class Matcher {

    final PsiElement pattern = null;
    final String replacement = "";

    public Matcher(final String p, final String r) {
        //pattern = extractStatementIfOne(wizard.ast(reformat(p)));
       // replacement = reformat(r);
    }

    public boolean blockMatches(final PsiElement ¢) {
        return blockMatches(pattern, ¢);
    }

    public boolean blockMatches(final PsiElement p, final PsiElement n) {
        if (!iz.block(n) || !iz.block(p))
            return false;
        @SuppressWarnings("unchecked") final List<PsiStatement> sp = Arrays.asList(az.block(p).getCodeBlock().getStatements());
        @SuppressWarnings("unchecked") final List<PsiStatement> sn = Arrays.asList(az.block(n).getCodeBlock().getStatements());
        if (sp == null || sn == null || sp.size() > sn.size())
            return false;
        for (int ¢ = 0; ¢ <= sn.size() - sp.size(); ++¢)
            if (statementsMatch(sp, sn.subList(¢, ¢ + sp.size())))
                return true;
        return false;
    }

    /** @param sp
     * @param subList
     * @return */
    private boolean statementsMatch(final List<PsiStatement> sp, final List<PsiStatement> subList) {
        for (int ¢ = 0; ¢ < sp.size(); ++¢)
            if (!matchesAux(sp.get(¢), subList.get(¢), new HashMap<>()))
                return false;
        return true;
    }

    //
    /** Tries to match a pattern <b>p</b> to a given ASTNode <b>n</b>, using<br>
     * the matching rules. For more info about these rules, see {@link Matcher}.
     * @param ¢ PsiElement
     * @return True iff <b>n</b> matches the pattern <b>p</b>. */
    public boolean matches(final PsiElement ¢) {
        return matchesAux(pattern, ¢, new HashMap<>());
    }

    @SuppressWarnings("unchecked") private boolean matchesAux(final PsiElement p, final PsiElement n, final Map<String, String> ids) {
        return false;

        /*
        if (p == null || n == null)
            return false;
        if (is$X(p))
            return iz.expression(n) && consistent(ids, p + "", n + "");
        if (iz.name(p))
            return sameName(p, n, ids);
        if (iz.literal(p))
            return sameLiteral(p, n);
        if (isBlockVariable(p))
            return matchesBlock(n) && consistent(ids, blockVariableName(p), n + "");
        if (isMethodInvocationAndHas$AArgument(p))
            return isMethodInvocationAndConsistentWith$AArgument(p, n, ids) && Recurser.children(n).size() == Recurser.children(p).size();
        if (isClassInstanceCreationAndHas$AArgument(p))
            return isClassInstanceCreationAndConsistentWith$AArgument(p, n) && Recurser.children(n).size() == Recurser.children(p).size();
        if (differentTypes(p, n))
            return false;
        if (iz.literal(p))
            return (p + "").equals(n + "");
        if (iz.containsOperator(p) && !sameOperator(p, n))
            return false;
        final List<? extends ASTNode> nChildren = Recurser.children(n);
        final List<? extends ASTNode> pChildren = Recurser.children(p);
        if (iz.methodInvocation(p)) {
            pChildren.addAll(az.methodInvocation(p).arguments());
            nChildren.addAll(az.methodInvocation(n).arguments());
        }
        if (nChildren.size() != pChildren.size())
            return false;
        for (int ¢ = 0; ¢ < pChildren.size(); ++¢)
            if (!matchesAux(pChildren.get(¢), nChildren.get(¢), ids))
                return false;
        return true;

    */

    }

    public PsiElement getMatching(final PsiElement n, final String s) {
        //return collectEnviromentNodes(n, new HashMap<>()).get(reformat(s));
        return null;
    }

    @SuppressWarnings("boxing") public Pair<Integer, Integer> getBlockMatching(final PsiBlockStatement p, final PsiBlockStatement n) {
        @SuppressWarnings("unchecked") final List<PsiStatement> sp = Arrays.asList(p.getCodeBlock().getStatements());
        @SuppressWarnings("unchecked") final List<PsiStatement> sn = Arrays.asList(n.getCodeBlock().getStatements());
        for (int ¢ = 0; ¢ <= sn.size() - sp.size(); ++¢)
            if (statementsMatch(sp, sn.subList(¢, ¢ + sp.size())))
                return new Pair<>(¢, ¢ + sp.size());
        return null;
    }

    /** @param b
     * @return */
    @SuppressWarnings("boxing") public PsiElement[] getMatchedNodes(final PsiBlockStatement b) {
        final Pair<Integer, Integer> idxs = getBlockMatching(az.block(pattern), b);
        final PsiElement[] $ = new PsiElement[idxs.second - idxs.first];
        for (int ¢ = idxs.first; ¢ < idxs.second; ++¢)
            $[¢ - idxs.first] = Arrays.asList(b.getCodeBlock().getStatements()).get(idxs.first);
        return $;
    }

}

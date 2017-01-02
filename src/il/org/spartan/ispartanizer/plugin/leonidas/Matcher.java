package il.org.spartan.ispartanizer.plugin.leonidas;

import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.auxilary_layer.step;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.util.PsiUtilCore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author Roey Maor
 * @since 3/12/2016.
 */


public class Matcher {

    final Supplier<PsiElement> patternSupplier;
    final String replacement;
    PsiElement __pattern;

    public Matcher(final String p, final String r) {
        //patternSupplier = () -> extractStatementIfOne(wizard.ast(reformat(p)));
        patternSupplier = null; //TODO: @RoeyMaor this is wrong
        replacement = reformat(r);
    }

    /*
        <N extends PsiElement> PsiElement replacement(final N n) {
            final Map<String, String> enviroment = collectEnviroment(n, new HashMap<>());
            final Wrapper<String> $ = new Wrapper<>();
            $.set(replacement);
            for (final String ¢ : enviroment.keySet())
                if (needsSpecialReplacement(¢))
                    $.set($.get().replace(¢, enviroment.get(¢) + ""));
            wizard.ast(replacement).accept(new ASTVisitor() {
                @Override public boolean preVisit2(final PsiElement ¢) {
                    if (iz.name(¢) && enviroment.containsKey(¢ + ""))
                        $.set($.get().replaceFirst((¢ + "").replace("$", "\\$"), enviroment.get(¢ + "").replace("\\", "\\\\").replace("$", "\\$") + ""));
                    return true;
                }
            });
            return extractStatementIfOne(wizard.ast($.get()));
        }
    */
    private static boolean needsSpecialReplacement(final String ¢) {
        return ¢.startsWith("$B") || matches$X(¢);
    }

    private static boolean matches$X(final String p) {
        return p.matches("\\$X\\d*\\(\\)");
    }

    private static boolean matches$T(final String p) {
        return p.matches("\\$T\\d*");
    }

    static PsiElement extractStatementIfOne(final PsiElement ¢) {
        return !iz.block(¢) || az.block(¢).getStatements().length != 1 ? ¢ : az.block(¢).getStatements()[0];
    }

    //

    /**
     * [[SuppressWarningsSpartan]]
     */
    private static Map<String, String> collectEnviroment(final PsiElement p, final PsiElement n, final Map<String, String> enviroment) {
        /*
        if (startsWith$notBlock(p))
            enviroment.put(p + "", n + "");
        else if (isBlockVariable(p))
            enviroment.put(blockVariableName(p) + "();", n + "");
        else {
            if (isMethodInvocationAndHas$AArgument(p))
                enviroment.put(argumentsId(p), arguments(n) + "");
            final List<PsiElement> pChildren = gatherChildren(p, p);
            final List<PsiElement> nChildren = gatherChildren(n, p);
            for (int ¢ = 0; ¢ < pChildren.size(); ++¢)
                collectEnviroment(pChildren.get(¢), nChildren.get(¢), enviroment);
        }
        return enviroment;
        */
        return null;
    }

    /**
     * @param ¢
     * @return
     */
    private static boolean is$T(final PsiElement ¢) {
        return iz.type(¢) && matches$T(¢ + "");
    }

    /**
     * @param ¢
     * @return
     */
    private static boolean is$X(final PsiElement ¢) {
        return iz.methodInvocation(¢) && matches$X(¢ + "");
    }

    private static boolean isBlockVariable(final PsiElement ¢) {
        if (!iz.block(¢) || step.statements(az.block(¢)).size() != 1)
            return false;
        final PsiStatement $ = step.statements(az.block(¢)).get(0);
        return iz.expressionStatement($) && iz.methodInvocation(az.expressionStatement($).getExpression()) && blockVariableName(¢).startsWith("$B");
    }

    private static String blockVariableName(final PsiElement ¢) {
        return PsiUtilCore.getName(az.methodInvocation(az.expressionStatement(step.statements(az.block(¢)).get(0)).getExpression()));
    }

    /**
     * @param ¢
     * @return
     */
    private static boolean isMethodInvocationAndHas$AArgument(final PsiElement ¢) {
        return iz.methodInvocation(¢) && az.methodInvocation(¢).getArgumentList().getExpressions().length == 1
                && (az.methodInvocation(¢).getArgumentList().getExpressions()[0] + "").startsWith("$A");
    }

    private static String argumentsId(final PsiElement ¢) {
        return az.methodInvocation(¢).getArgumentList().getExpressions()[0] + "";
    }

    private static String arguments(final PsiElement ¢) {
        final String $ = az.methodInvocation(¢).getArgumentList() + "";
        return $.substring(1, $.length() - 1);
    }

    static String reformat(final String ¢) {
        return ¢.replaceAll("\\$B\\d*", "{$0\\(\\);}").replaceAll("\\$X\\d*", "$0\\(\\)");
    }

    PsiElement pattern() {
        return __pattern != null ? __pattern : (__pattern = patternSupplier.get());
    }

    public boolean blockMatches(final PsiElement ¢) {
        return blockMatches(__pattern, ¢);
    }
/*
    private static boolean startsWith$notBlock(final PsiElement p) {
        return is$X(p) || iz.name(p) && ((p + "").startsWith("$M") || (p + "").startsWith("$N") || (p + "").startsWith("$L")) || is$T(p);
    }
    */

    public boolean blockMatches(final PsiElement e, final PsiElement n) {
        if (!iz.block(n) || !iz.block(e))
            return false;
        @SuppressWarnings("unchecked") final List<PsiStatement> sp = Arrays.asList(az.block(e).getStatements());
        @SuppressWarnings("unchecked") final List<PsiStatement> sn = Arrays.asList(az.block(n).getStatements());
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

    /** Tries to match a pattern <b>p</b> to a given ASTNode <b>n</b>, using<br>
     * the matching rules. For more info about these rules, see {@link Matcher}.
     * @param ¢ PsiElement
     * @return True iff <b>n</b> matches the pattern <b>p</b>. */
    public boolean matches(final PsiElement ¢) {
        return matchesAux(__pattern, ¢, new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    private boolean matchesAux(final PsiElement __, final PsiElement e, final Map<String, String> ids) {
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

    public PsiElement getMatching(final PsiElement __, final String s) {
        //return collectEnviromentNodes(n, new HashMap<>()).get(reformat(s));
        return null;
    }

    @SuppressWarnings("boxing")
    public Pair<Integer, Integer> getBlockMatching(final PsiCodeBlock b, final PsiCodeBlock n) {
        @SuppressWarnings("unchecked") final List<PsiStatement> sp = Arrays.asList(b.getStatements());
        @SuppressWarnings("unchecked") final List<PsiStatement> sn = Arrays.asList(n.getStatements());
        for (int ¢ = 0; ¢ <= sn.size() - sp.size(); ++¢)
            if (statementsMatch(sp, sn.subList(¢, ¢ + sp.size())))
                return new Pair<>(¢, ¢ + sp.size());
        return null;
    }

    /** @param b
     * @return */
    @SuppressWarnings("boxing") public PsiElement[] getMatchedNodes(final PsiCodeBlock b) {
        final Pair<Integer, Integer> idxs = getBlockMatching(az.block(__pattern), b);
        final PsiElement[] $ = new PsiElement[idxs.second - idxs.first];
        for (int ¢ = idxs.first; ¢ < idxs.second; ++¢)
            $[¢ - idxs.first] = Arrays.asList(b.getStatements()).get(idxs.first);
        return $;
    }
/*
    @SuppressWarnings("unchecked") private static List<PsiElement> gatherChildren(final PsiElement ¢, final PsiElement p) {
        final List<PsiElement> $ = (List<PsiElement>) Recurser.children(¢);
        if (iz.methodInvocation(¢)) {
            if (!isMethodInvocationAndHas$AArgument(p))
                $.addAll(az.methodInvocation(¢).arguments());
            if (haz.expression(az.methodInvocation(¢)))
                $.add(step.expression(az.methodInvocation(¢)));
        }
        if (iz.forStatement(¢)) {
            $.addAll(step.initializers(az.forStatement(¢)));
            $.add(step.condition(az.forStatement(¢)));
            $.addAll(step.updaters(az.forStatement(¢)));
        }
        if (iz.variableDeclarationExpression(¢))
            $.addAll(step.fragments(az.variableDeclarationExpression(¢)));
        return $;
    }
    */

    public Map<String, String> collectEnviroment(final PsiElement e, final Map<String, String> enviroment) {
        return collectEnviroment(pattern(), e, enviroment);
    }

}

package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;

/**
 * @author michal cohen
 * @since 22-12-2016
 */
public class Unless extends NanoPatternTipper<PsiConditionalExpression> {

    /**
     * @param ¢
     * @return true iff e is in the form: <boolean expression> ? null : <expression>
     */
    @Override
    public boolean canTip(PsiElement ¢) {
        return (iz.conditionalExpression(¢) && (iz.nullExpression(az.conditionalExpression(¢).getThenExpression())));
    }

    @Override
    public String description() {
        return "Change conditional expression to fluent Unless";
    }

    @Override
    public String description(PsiConditionalExpression ¢) {
        return "Change " + ¢.getText() + " to fluent eval().unless()";
    }

    /**
     * @param ¢ - the element to be replaced
     * @return a method invocation to unless function.
     */
    @Override
    public PsiElement createReplacement(PsiConditionalExpression ¢) {
        return JavaPsiFacade.getElementFactory(¢.getProject()).createExpressionFromText("eval(" + ¢.getElseExpression().getText() + ").unless( " + ¢.getCondition().getText() + ")", ¢);
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return PsiConditionalExpression.class;
    }

    @Override
    protected Tip pattern(final PsiConditionalExpression ¢) {
        return tip(¢);
    }

}

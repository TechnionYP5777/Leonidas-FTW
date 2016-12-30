package plugin.tippers;

import auxilary_layer.az;
import auxilary_layer.iz;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;
import plugin.tipping.Tip;

/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class Unless extends NanoPatternTipper<PsiConditionalExpression> {

    @Override
    public boolean canTip(PsiElement e) {
        return (iz.conditionalExpression(e) && (iz.nullExpression(az.conditionalExpression(e).getThenExpression())));
    }

    @Override
    public String description() {
        return "Change conditional expression to fluent Unless";
    }

    @Override
    public String description(PsiConditionalExpression psiConditionalExpression) {
        return "Change conditional expression: " + psiConditionalExpression.getText() + " to fluent Unless";
    }

    @Override
    protected PsiElement createReplacement(PsiConditionalExpression e) {
        return JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("unless(" + e.getCondition().getText() + ", " + e.getElseExpression().getText() + ")", e);
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

package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;

/**
 * This is a safeReference Nano pattern.
 * This tip works only for field accesses and for Method calls with no params.
 *
 * @author amirsagiv
 * @since 23-12-2016
 */
public class SafeReference extends NanoPatternTipper<PsiConditionalExpression> {
    @Override
    public boolean canTip(PsiElement e) {
        return firstScenario(e) || secondScenario(e) || thirdScenario(e) || fourthScenario(e);

    }

    @Override
    public String description(PsiConditionalExpression psiConditionalExpression) {
        return "Replace null conditional ternary with ?.";
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public PsiElement createReplacement(PsiConditionalExpression e) {

        String replacementString;
        if (firstScenario(e) || secondScenario(e)) {
            if (iz.referenceExpression(az.conditionalExpression(e).getElseExpression())) {
                replacementString = "nullConditional(" + az.referenceExpression(az.conditionalExpression(e).getElseExpression()).getQualifier().getText()
                        + " , ¢ -> ¢." + az.referenceExpression(az.conditionalExpression(e).getElseExpression()).getReferenceNameElement().getText() + ")";
            } else {
                replacementString = "nullConditional(" + az.methodCallExpression(az.conditionalExpression(e).getElseExpression()).getMethodExpression().getQualifier().getText()
                        + " , ¢ -> ¢." + az.methodCallExpression(az.conditionalExpression(e).getElseExpression()).getMethodExpression().getReferenceNameElement().getText() + "())";
            }
        } else { // third or fourth Scenarios
            if (iz.referenceExpression(az.conditionalExpression(e).getThenExpression())) {
                replacementString = "nullConditional(" + az.referenceExpression(az.conditionalExpression(e).getThenExpression()).getQualifier().getText()
                        + " , ¢ -> ¢." + az.referenceExpression(az.conditionalExpression(e).getThenExpression()).getReferenceNameElement().getText() + ")";
            } else {
                replacementString = "nullConditional(" + az.methodCallExpression(az.conditionalExpression(e).getThenExpression()).getMethodExpression().getQualifier().getText()
                        + " , ¢ -> ¢." + az.methodCallExpression(az.conditionalExpression(e).getThenExpression()).getMethodExpression().getReferenceNameElement().getText() + "())";
            }
        }
        return JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText(replacementString, e);
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return PsiConditionalExpression.class;
    }

    @SuppressWarnings("ConstantConditions")
    private boolean firstScenario(PsiElement e) {
        // (x == null) ? null : x.y
        boolean cond1 = iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign().getText().equals("==")) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getThenExpression());

        boolean cond2 = cond1 && iz.referenceExpression(az.conditionalExpression(e).getElseExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getElseExpression()).getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand().getText()));

        boolean cond3 = cond1 && iz.methodCallExpression(az.conditionalExpression(e).getElseExpression()) &&
                (az.methodCallExpression(az.conditionalExpression(e).getElseExpression()).getMethodExpression().getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand().getText())) &&
                az.methodCallExpression(az.conditionalExpression(e).getElseExpression()).getArgumentList().getExpressions().length == 0;

        return (cond2 || cond3);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean secondScenario(PsiElement e) {
        // (null == x) ? null : x.y
        boolean cond1 = iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign().getText().equals("==")) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getThenExpression());

        boolean cond2 = cond1 && iz.referenceExpression(az.conditionalExpression(e).getElseExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getElseExpression()).getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand().getText()));

        boolean cond3 = cond1 && iz.methodCallExpression(az.conditionalExpression(e).getElseExpression()) &&
                (az.methodCallExpression(az.conditionalExpression(e).getElseExpression()).getMethodExpression().getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand().getText())) &&
                az.methodCallExpression(az.conditionalExpression(e).getElseExpression()).getArgumentList().getExpressions().length == 0;

        return (cond2 || cond3);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean thirdScenario(PsiElement e) {
        // (x != null) ? x.y : null
        boolean cond1 = iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign().getText().equals("!=")) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getElseExpression());

        boolean cond2 = cond1 && iz.referenceExpression(az.conditionalExpression(e).getThenExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getThenExpression()).getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand().getText()));

        boolean cond3 = cond1 && iz.methodCallExpression(az.conditionalExpression(e).getThenExpression()) &&
                (az.methodCallExpression(az.conditionalExpression(e).getThenExpression()).getMethodExpression().getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand().getText())) &&
                az.methodCallExpression(az.conditionalExpression(e).getThenExpression()).getArgumentList().getExpressions().length == 0;

        return (cond2 || cond3);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean fourthScenario(PsiElement e) {
        // (null != x.y) ? x.y : null
        boolean cond1 = iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign().getText().equals("!=")) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getElseExpression());

        boolean cond2 = cond1 && iz.referenceExpression(az.conditionalExpression(e).getThenExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getThenExpression()).getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand().getText()));

        boolean cond3 = cond1 && iz.methodCallExpression(az.conditionalExpression(e).getThenExpression()) &&
                (az.methodCallExpression(az.conditionalExpression(e).getThenExpression()).getMethodExpression().getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand().getText())) &&
                az.methodCallExpression(az.conditionalExpression(e).getThenExpression()).getArgumentList().getExpressions().length == 0;

        return (cond2 || cond3);
    }

    @Override
    protected Tip pattern(final PsiConditionalExpression ¢) {
        return tip(¢);
    }

}
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
    public boolean canTip(PsiElement ¢) {
        return firstScenario(¢) || secondScenario(¢) || thirdScenario(¢) || fourthScenario(¢);

    }

    @Override
    public String description(PsiConditionalExpression __) {
        return "Replace null conditional ternary with ?.";
    }

    @Override
    public PsiElement createReplacement(PsiConditionalExpression ¢) {

        return JavaPsiFacade
                .getElementFactory(
                        ¢.getProject())
                .createExpressionFromText(
                        ("nullConditional(" + (firstScenario(¢) || secondScenario(¢)
                                ? iz.referenceExpression(az.conditionalExpression(¢).getElseExpression()) ? az.referenceExpression(az.conditionalExpression(¢).getElseExpression()).getQualifier()
                                .getText() + " , ¢ -> ¢."
                                + az.referenceExpression(az.conditionalExpression(¢).getElseExpression())
                                .getReferenceNameElement().getText()
                                : az.methodCallExpression(az.conditionalExpression(¢).getElseExpression())
                                .getMethodExpression().getQualifier().getText()
                                + " , ¢ -> ¢."
                                + az.methodCallExpression(
                                az.conditionalExpression(¢).getElseExpression())
                                .getMethodExpression().getReferenceNameElement().getText()
                                + "()"
                                : iz.referenceExpression(az.conditionalExpression(¢).getThenExpression())
                                ? az.referenceExpression(az.conditionalExpression(¢).getThenExpression())
                                .getQualifier().getText()
                                + " , ¢ -> ¢."
                                + az.referenceExpression(
                                az.conditionalExpression(¢).getThenExpression())
                                .getReferenceNameElement().getText()
                                : az.methodCallExpression(az.conditionalExpression(¢).getThenExpression())
                                .getMethodExpression().getQualifier().getText()
                                + " , ¢ -> ¢."
                                + az.methodCallExpression(
                                az.conditionalExpression(¢).getThenExpression())
                                .getMethodExpression().getReferenceNameElement().getText()
                                + "()")
                                + ")"),
                        ¢);
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return PsiConditionalExpression.class;
    }

    private boolean firstScenario(PsiElement ¢) {
        return (iz.conditionalExpression(¢) && iz.binaryExpression(az.conditionalExpression(¢).getCondition())
                && ("==".equals(
                az.binaryExpression(az.conditionalExpression(¢).getCondition()).getOperationSign().getText()))
                && iz.nullExpression(az.binaryExpression(az.conditionalExpression(¢).getCondition()).getROperand())
                && iz.nullExpression(az.conditionalExpression(¢).getThenExpression())
                && (iz.referenceExpression(az.conditionalExpression(¢).getElseExpression())
                && az.referenceExpression(az.conditionalExpression(¢).getElseExpression()).getQualifier()
                .getText()
                .equals(az.binaryExpression(az.conditionalExpression(¢).getCondition()).getLOperand()
                        .getText())
                || iz.methodCallExpression(az.conditionalExpression(¢).getElseExpression())
                && az.methodCallExpression(az.conditionalExpression(¢).getElseExpression())
                .getMethodExpression().getQualifier().getText()
                .equals(az.binaryExpression(az.conditionalExpression(¢).getCondition())
                        .getLOperand().getText())
                && az.methodCallExpression(az.conditionalExpression(¢).getElseExpression())
                .getArgumentList().getExpressions().length == 0));
    }

    private boolean secondScenario(PsiElement ¢) {
        return (iz.conditionalExpression(¢) && iz.binaryExpression(az.conditionalExpression(¢).getCondition())
                && ("==".equals(
                az.binaryExpression(az.conditionalExpression(¢).getCondition()).getOperationSign().getText()))
                && iz.nullExpression(az.binaryExpression(az.conditionalExpression(¢).getCondition()).getLOperand())
                && iz.nullExpression(az.conditionalExpression(¢).getThenExpression())
                && (iz.referenceExpression(az.conditionalExpression(¢).getElseExpression())
                && az.referenceExpression(az.conditionalExpression(¢).getElseExpression()).getQualifier()
                .getText()
                .equals(az.binaryExpression(az.conditionalExpression(¢).getCondition()).getROperand()
                        .getText())
                || iz.methodCallExpression(az.conditionalExpression(¢).getElseExpression())
                && az.methodCallExpression(az.conditionalExpression(¢).getElseExpression())
                .getMethodExpression().getQualifier().getText()
                .equals(az.binaryExpression(az.conditionalExpression(¢).getCondition())
                        .getROperand().getText())
                && az.methodCallExpression(az.conditionalExpression(¢).getElseExpression())
                .getArgumentList().getExpressions().length == 0));
    }

    private boolean thirdScenario(PsiElement ¢) {
        return (iz.conditionalExpression(¢) && iz.binaryExpression(az.conditionalExpression(¢).getCondition())
                && ("!=".equals(
                az.binaryExpression(az.conditionalExpression(¢).getCondition()).getOperationSign().getText()))
                && iz.nullExpression(az.binaryExpression(az.conditionalExpression(¢).getCondition()).getROperand())
                && iz.nullExpression(az.conditionalExpression(¢).getElseExpression())
                && (iz.referenceExpression(az.conditionalExpression(¢).getThenExpression())
                && az.referenceExpression(az.conditionalExpression(¢).getThenExpression()).getQualifier()
                .getText()
                .equals(az.binaryExpression(az.conditionalExpression(¢).getCondition()).getLOperand()
                        .getText())
                || iz.methodCallExpression(az.conditionalExpression(¢).getThenExpression())
                && az.methodCallExpression(az.conditionalExpression(¢).getThenExpression())
                .getMethodExpression().getQualifier().getText()
                .equals(az.binaryExpression(az.conditionalExpression(¢).getCondition())
                        .getLOperand().getText())
                && az.methodCallExpression(az.conditionalExpression(¢).getThenExpression())
                .getArgumentList().getExpressions().length == 0));
    }

    private boolean fourthScenario(PsiElement ¢) {
        return (iz.conditionalExpression(¢) && iz.binaryExpression(az.conditionalExpression(¢).getCondition())
                && ("!=".equals(
                az.binaryExpression(az.conditionalExpression(¢).getCondition()).getOperationSign().getText()))
                && iz.nullExpression(az.binaryExpression(az.conditionalExpression(¢).getCondition()).getLOperand())
                && iz.nullExpression(az.conditionalExpression(¢).getElseExpression())
                && (iz.referenceExpression(az.conditionalExpression(¢).getThenExpression())
                && az.referenceExpression(az.conditionalExpression(¢).getThenExpression()).getQualifier()
                .getText()
                .equals(az.binaryExpression(az.conditionalExpression(¢).getCondition()).getROperand()
                        .getText())
                || iz.methodCallExpression(az.conditionalExpression(¢).getThenExpression())
                && az.methodCallExpression(az.conditionalExpression(¢).getThenExpression())
                .getMethodExpression().getQualifier().getText()
                .equals(az.binaryExpression(az.conditionalExpression(¢).getCondition())
                        .getROperand().getText())
                && az.methodCallExpression(az.conditionalExpression(¢).getThenExpression())
                .getArgumentList().getExpressions().length == 0));
    }

    @Override
    protected Tip pattern(final PsiConditionalExpression ¢) {
        return tip(¢);
    }

}
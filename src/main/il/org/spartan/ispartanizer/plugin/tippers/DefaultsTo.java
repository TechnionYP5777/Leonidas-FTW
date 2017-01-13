package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.*;
import com.intellij.psi.tree.IElementType;
import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.haz;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.auxilary_layer.step;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;

/**
 * Replace X != null ? X : Y with defaults(X).to(Y) <br>
 * Replace X == null ? Y : X with defaults(X).to(Y) <br>
 * Replace null == X ? Y : X with defaults(X).to(Y) <br>
 * Replace null != X ? X : Y with defaults(X).to(Y) <br>
 *
 * @author Oren Afek
 * @since 24-12-2016
 */

public class DefaultsTo extends NanoPatternTipper<PsiConditionalExpression> {

    @Override
    public boolean canTip(PsiElement e) {

        if (!(iz.conditionalExpression(e) &&
                iz.binaryExpression(step.conditionExpression(az.conditionalExpression(e))))) {
            return false;
        }
        PsiConditionalExpression conditionalExpression = az.conditionalExpression(e);
        PsiBinaryExpression condition = az.binaryExpression(step.conditionExpression(conditionalExpression));
        return (haz.equalsOperator(condition) || haz.notEqualsOperator(condition)) &&
                isExactlyOneOfTheArgsNull(step.leftOperand(condition), step.rightOperand(condition)) &&
                areOperandsEqualsToBranches(conditionalExpression);

    }

    private boolean isExactlyOneOfTheArgsNull(PsiExpression lArg, PsiExpression rArg) {
        return (iz.null$(lArg) && iz.notNull(rArg)) || (iz.null$(rArg) && iz.notNull(lArg));
    }

    private boolean areOperandsEqualsToBranches(PsiConditionalExpression conditionalExpression) {
        PsiBinaryExpression condition = az.binaryExpression(step.conditionExpression(conditionalExpression));
        IElementType operator = step.operator(condition);
        PsiExpression lOp = step.leftOperand(condition);
        PsiExpression rOp = step.rightOperand(condition);
        PsiExpression thenExpr = step.thenExpression(conditionalExpression);
        PsiExpression elseExpr = step.elseExpression(conditionalExpression);
        return (iz.notNull(lOp) && ((iz.equalsOperator(operator) && lOp.getText().equals(elseExpr.getText())) ||
                (iz.notEqualsOperator(operator)) && lOp.getText().equals(thenExpr.getText()))) ||
                (iz.null$(lOp) && ((iz.equalsOperator(operator) && rOp.getText().equals(elseExpr.getText())) ||
                        (iz.notEqualsOperator(operator)) && rOp.getText().equals(thenExpr.getText())));
    }

    @Override
    public String description(PsiConditionalExpression psiConditionalExpression) {
        return "Replace X != null ? X : Y with X ?? Y <br>\n" +
                "Replace X == null ? Y : X with X ?? Y <br>\n" +
                "Replace null == X ? Y : X with X ?? Y <br>\n" +
                "Replace null != X ? X : Y with X ?? Y <br>";
    }

    private boolean eqOperator(PsiConditionalExpression e) {
        PsiBinaryExpression condition = az.binaryExpression(step.conditionExpression(e));
        IElementType operator = step.operator(condition);
        return iz.equalsOperator(operator);
    }

    @Override
    public PsiElement createReplacement(PsiConditionalExpression e) {
        String replacement = "defaults(" +
                (eqOperator(e) ? e.getElseExpression().getText() : e.getThenExpression().getText()) +
                ")" +
                ".to(" +
                (eqOperator(e) ? e.getThenExpression().getText() : e.getElseExpression().getText()) +
                ")";

        return JavaPsiFacade.getElementFactory(e.getProject())
                .createExpressionFromText(replacement, e);
    }

    @Override
    protected Tip pattern(PsiConditionalExpression ¢) {
        return null;
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return PsiConditionalExpression.class;
    }
}
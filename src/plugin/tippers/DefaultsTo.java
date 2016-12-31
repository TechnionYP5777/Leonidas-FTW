package plugin.tippers;

import auxilary_layer.*;
import com.intellij.psi.*;
import com.intellij.psi.tree.IElementType;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

/**
 * Replace X != null ? X : Y with X ?? Y <br>
 * Replace X == null ? Y : X with X ?? Y <br>
 * Replace null == X ? Y : X with X ?? Y <br>
 * Replace null != X ? X : Y with X ?? Y <br>
 *
 * @author Oren Afek
 * @since 2016.12.24
 */

public class DefaultsTo implements Tipper<PsiConditionalExpression> {

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

    @Override
    public Tip tip(PsiConditionalExpression node) {
        final PsiBinaryExpression binaryExpression = az.binaryExpression(step.conditionExpression(node));
        final String leftOperandText = String.valueOf(step.leftOperand(binaryExpression).textToCharArray());
        final String rightOperandText = String.valueOf(step.rightOperand(binaryExpression).textToCharArray());
        final String replacementFormat = "%s ?? %s";
        return new Tip(description(node), node, this.getClass()) {
            @Override
            public void go(PsiRewrite r) {
                String replacementString;
                // if (cond: (X == null) or cond: (null == X))
                if (iz.equalsOperator(step.operator(binaryExpression))) {
                    replacementString = String.format(replacementFormat, rightOperandText, leftOperandText);
                } else {
                    replacementString = String.format(replacementFormat, leftOperandText, rightOperandText);
                }

                PsiElement replacement = JavaPsiFacade.getElementFactory(node.getProject())
                        .createExpressionFromText(replacementString, node);

                r.replace(node, replacement);
            }
        };
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return PsiConditionalExpression.class;
    }
}
package plugin.tippers;

import auxilary_layer.az;
import auxilary_layer.iz;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

/**
 * Created by amirsagiv on 12/23/16.
 */
public class SafeReference implements Tipper<PsiConditionalExpression> {
    @Override
    public boolean canTip(PsiElement e) {
        // (x == null) ? null : x.y
        boolean firstSenario =  iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign() == JavaTokenType.EQEQ) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getThenExpression()) &&
                iz.referenceExpression(az.conditionalExpression(e).getElseExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getElseExpression()).getReferenceNameElement().getText() ==
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand().getText());
        // (null == x) ? null : x.y
        boolean secondSenario =  iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign() == JavaTokenType.EQEQ) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getThenExpression()) &&
                iz.referenceExpression(az.conditionalExpression(e).getElseExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getElseExpression()).getReferenceNameElement().getText() ==
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand().getText());
        // (x != null) ? x.y : null
        boolean thirdSenario =  iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign() == JavaTokenType.NE) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getElseExpression()) &&
                iz.referenceExpression(az.conditionalExpression(e).getThenExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getThenExpression()).getReferenceNameElement().getText() ==
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand().getText());
        // (null != x.y) ? x.y : null
        boolean fourthSenario =  iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign() == JavaTokenType.NE) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getElseExpression()) &&
                iz.referenceExpression(az.conditionalExpression(e).getThenExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getThenExpression()).getReferenceNameElement().getText() ==
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand().getText());

        return firstSenario || secondSenario || thirdSenario || fourthSenario;



    }

    @Override
    public String description(PsiConditionalExpression psiConditionalExpression) {
        return null;
    }

    @Override
    public Tip tip(PsiConditionalExpression node) {
        return null;
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return null;
    }
}

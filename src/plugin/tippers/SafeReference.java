package plugin.tippers;

import auxilary_layer.PsiRewrite;
import auxilary_layer.az;
import auxilary_layer.iz;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiStatement;
import plugin.tipping.Tip;

/**
 * Created by amirsagiv on 12/23/16.
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

    @Override
    public Tip tip(PsiConditionalExpression node) {

        return node == null || !canTip(node) ? null : new Tip(description(node), node, this.getClass()) {
            @Override
            public void go(final PsiRewrite r) {
                String replacementString;
                if(firstScenario(node) || secondScenario(node)){
                    replacementString = az.referenceExpression(az.conditionalExpression(node).getElseExpression()).getQualifier().getText()
                                        + "?." + az.referenceExpression(az.conditionalExpression(node).getElseExpression()).getReferenceNameElement().getText();
                }else{ // third or fourth Scenarios
                    replacementString = az.referenceExpression(az.conditionalExpression(node).getThenExpression()).getQualifier().getText()
                            + "?." + az.referenceExpression(az.conditionalExpression(node).getThenExpression()).getReferenceNameElement().getText();
                }
                PsiStatement replacement = JavaPsiFacade.getElementFactory(node.getProject()).createStatementFromText("\""+ replacementString+ "\"",node);
                r.replace(node, replacement);

            }
        };
    }

    @Override
    protected PsiElement createReplacement(PsiConditionalExpression e) {
        return null;
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return PsiConditionalExpression.class;
    }

    private boolean firstScenario(PsiElement e){
        // (x == null) ? null : x.y
        return  iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign().getText().equals("==")) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getThenExpression()) &&
                iz.referenceExpression(az.conditionalExpression(e).getElseExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getElseExpression()).getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand().getText()));
    }

    private boolean secondScenario(PsiElement e){
        // (null == x) ? null : x.y
        return  iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign().getText().equals("==")) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getThenExpression()) &&
                iz.referenceExpression(az.conditionalExpression(e).getElseExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getElseExpression()).getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand().getText()));
    }

    private boolean thirdScenario(PsiElement e){
        // (x != null) ? x.y : null
        return  iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign().getText().equals("!=")) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getElseExpression()) &&
                iz.referenceExpression(az.conditionalExpression(e).getThenExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getThenExpression()).getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand().getText()));
    }

    private boolean fourthScenario(PsiElement e){
        // (null != x.y) ? x.y : null
        return  iz.conditionalExpression(e) && iz.binaryExpression(az.conditionalExpression(e).getCondition()) &&
                (az.binaryExpression(az.conditionalExpression(e).getCondition()).getOperationSign().getText().equals("!=")) &&
                iz.nullExpression(az.binaryExpression(az.conditionalExpression(e).getCondition()).getLOperand()) &&
                iz.nullExpression(az.conditionalExpression(e).getElseExpression()) &&
                iz.referenceExpression(az.conditionalExpression(e).getThenExpression()) &&
                (az.referenceExpression(az.conditionalExpression(e).getThenExpression()).getQualifier().getText().equals(
                        az.binaryExpression(az.conditionalExpression(e).getCondition()).getROperand().getText()));
    }

    @Override
    protected Tip pattern(final PsiConditionalExpression ¢){
        return tip(¢);
    }


}

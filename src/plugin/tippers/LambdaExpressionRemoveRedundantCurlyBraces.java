package plugin.tippers;

import auxilary_layer.iz;
import auxilary_layer.step;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLambdaExpression;
import com.intellij.psi.PsiReturnStatement;
import com.intellij.psi.PsiStatement;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;
import auxilary_layer.PsiRewrite;

/**
 * @author Oren Afek
 * @since 2016.12.1
 */

public class LambdaExpressionRemoveRedundantCurlyBraces implements Tipper<PsiLambdaExpression> {

    @Override
    public boolean canTip(PsiElement e) {
        return e instanceof PsiLambdaExpression && canTip((PsiLambdaExpression) e);
    }

    private boolean canTip(PsiLambdaExpression element) {
        return iz.block(element.getBody()) && step.statements(step.blockBody(element)).size() == 1 //
                && iz.returnStatement(step.statements(step.blockBody(element)).get(0));
    }

    @Override
    public String description(PsiLambdaExpression lambdaExpression) {
        return null;
    }

    @Override
    public Tip tip(final PsiLambdaExpression element) {
        assert step.statements(step.blockBody(element)).size() == 1;
        final PsiStatement s = step.statements(step.blockBody(element)).get(0);

        return new Tip(description(element), element, this.getClass()) {
            @Override public void go(final PsiRewrite r){
                if (iz.returnStatement(s)) {
                    r.replace(element.getBody(), step.expression((PsiReturnStatement) s));
                }
            }
        };

    }

    @Override
    public Class<PsiLambdaExpression> getPsiClass() {
        return PsiLambdaExpression.class;
    }

}

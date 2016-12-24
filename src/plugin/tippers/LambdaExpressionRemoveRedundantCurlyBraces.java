package plugin.tippers;

import auxilary_layer.PsiRewrite;
import auxilary_layer.iz;
import com.intellij.psi.*;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

import static auxilary_layer.step.*;

/**
 * @author Oren Afek
 * @since 2016.12.1
 */

public class LambdaExpressionRemoveRedundantCurlyBraces implements Tipper<PsiLambdaExpression> {

    @Override
    public boolean canTip(PsiElement ¢) {
        return ¢ instanceof PsiLambdaExpression && canTip((PsiLambdaExpression) ¢);
    }

    private boolean canTip(PsiLambdaExpression element) {
        return iz.block(element.getBody()) && statements(blockBody(element)).size() == 1 //
                && (isCandidateReturnStatement(element) || isCandidateStatementExpression(element));
    }

    private boolean isCandidateReturnStatement(PsiLambdaExpression element) {
        return iz.returnStatement(firstStatement(blockBody(element)));
    }

    private boolean isCandidateStatementExpression(PsiLambdaExpression element) {
        return iz.expressionStatement(firstStatement(blockBody(element)));
    }

    @Override
    public String description(PsiLambdaExpression __) {
        return null;
    }

    @Override
    public Tip tip(final PsiLambdaExpression element) {
        assert statements(blockBody(element)).size() == 1;
        final PsiStatement s = firstStatement(blockBody(element));

        return new Tip(description(element), element, this.getClass()) {
            @Override
            public void go(final PsiRewrite r) {
                if (isCandidateReturnStatement(element)) {
                    r.replace(element.getBody(), expression((PsiReturnStatement) s));
                }

                if (isCandidateStatementExpression(element)) {
                    r.replace(element.getBody(), expression((PsiExpressionStatement) s));
                }

            }
        };

    }

    @Override
    public Class<PsiLambdaExpression> getPsiClass() {
        return PsiLambdaExpression.class;
    }

}
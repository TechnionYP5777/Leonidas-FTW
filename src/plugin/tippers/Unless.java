package plugin.tippers;

import auxilary_layer.PsiRewrite;
import auxilary_layer.az;
import auxilary_layer.iz;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
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
    public Tip tip(PsiConditionalExpression e) {
        if (!canTip(e)) return null;
        return new Tip(description(e), e, this.getClass()) {
            @Override
            public void go(PsiRewrite r) {

                PsiExpression e_tag = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("unless(" + e.getCondition().getText() + ", " + e.getElseExpression().getText() + ")", e);

                if (iz.nullExpression(az.conditionalExpression(e).getThenExpression())) {

                    new WriteCommandAction.Simple(e.getProject(), e.getContainingFile()) {
                        @Override
                        protected void run() throws Throwable {
                            createEnvironment(e);
                            e.replace(e_tag);
                        }
                    }.execute();
                }
            }
        };
    }


    @Override
    protected PsiConditionalExpression createReplacement(PsiConditionalExpression e) {
        return az.conditionalExpression(JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("unless(" + e.getCondition().getText() + ", " + e.getElseExpression().getText() + ")", e));
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

package plugin.tippers;

import auxilary_layer.PsiRewrite;
import auxilary_layer.az;
import auxilary_layer.haz;
import auxilary_layer.iz;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class Unless implements Tipper<PsiConditionalExpression> {

    @Override
    public boolean canTip(PsiElement e) {
        return (iz.conditionalExpression(e) && (iz.nullExpression(az.conditionalExpression(e).getThenExpression()) || iz.nullExpression(az.conditionalExpression(e).getElseExpression())) && !haz.functionNamed(e.getContext(), "Unless"));
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
            public void go(PsiRewrite _) {
                PsiClass c = JavaPsiFacade.getElementFactory(e.getProject()).createClass(" public class Unless{private boolean x; public Unless(boolean y){ x = y; } T eval<T>(T z){ return x ? null : z; } ");
                PsiMethod m = JavaPsiFacade.getElementFactory(e.getProject()).createMethod("Unless unless(boolean x){ return new Unless(x); ", PsiType.getTypeByName("Unless", e.getProject(), GlobalSearchScope.allScope(e.getProject())));
                PsiExpression e_tag = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("unless(" + e.getCondition().getText() + ").eval(" + e.getElseExpression().getText() + ");", e);
                if (iz.nullExpression(az.conditionalExpression(e).getThenExpression())) {
                    e.getContainingFile().add(c);
                    e.getContainingFile().add(m);
                    e.replace(e_tag);
                }

            }
        };
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return PsiConditionalExpression.class;
    }
}

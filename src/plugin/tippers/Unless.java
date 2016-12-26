package plugin.tippers;

import auxilary_layer.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class Unless extends NanoPatternTipper<PsiConditionalExpression> {

    @Override
    public boolean canTip(PsiElement e) {
        return (iz.conditionalExpression(e) && (iz.nullExpression(az.conditionalExpression(e).getThenExpression()) || iz.nullExpression(az.conditionalExpression(e).getElseExpression())) && !haz.functionNamed(e.getContainingFile().getNode().getPsi(), "Unless"));
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
                String elseType = e.getElseExpression().getType().getCanonicalText();
                //PsiFileFactory.getInstance(e.getProject()).createFileFromText("Unless.java", FileTypeRegistry.getInstance().getFileTypeByFileName("Unless.java"), "public class Unless { boolean x; Unless(boolean y) { this.x = y; } " + elseType + " eval(" + elseType + " z) { return x ? null : z; } }");

                PsiMethod p = (PsiMethod) PsiFileFactory.getInstance(e.getProject()).createFileFromText("Unless.java", FileTypeRegistry.getInstance().getFileTypeByFileName("Unless.java"), "public class Banana { static " + elseType + " unless(boolean x, " + elseType + " y) { return x ? null : y; } }").getFirstChild().getNextSibling().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling();
                PsiExpression e_tag = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("unless(" + e.getCondition().getText() + ", " + e.getElseExpression().getText() + ")", e);

                if (iz.nullExpression(az.conditionalExpression(e).getThenExpression())) {
                    //e.getContainingFile().add(c);

                    new WriteCommandAction.Simple(e.getProject(), e.getContainingFile()) {
                        @Override
                        protected void run() throws Throwable {
                            Utils.getCountainingClass(e).addBefore(p, Utils.getCountainingClass(e).getRBrace());
                            e.replace(e_tag);

                        }

                    }.execute();

                    //e.replace(e_tag);

                }
            }
        };
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return PsiConditionalExpression.class;
    }

    @Override
    protected Tip pattern(final PsiConditionalExpression ¢){
        return tip(¢);
    }

}

package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;

/**
 * @author AnnaBel7
 * @since 23-12-2016
 */
public class LispLastElement extends NanoPatternTipper<PsiMethodCallExpression> {

    private boolean canTip(PsiMethodCallExpression x) {
        PsiReferenceExpression[] outterReference = PsiTreeUtil.getChildrenOfType(x.getMethodExpression(),
                PsiReferenceExpression.class);
        PsiIdentifier[] outterIdentifier = PsiTreeUtil.getChildrenOfType(x.getMethodExpression(), PsiIdentifier.class);
        if (outterReference == null || outterIdentifier == null || outterReference.length != 1
                || outterIdentifier.length != 1 || !"get".equals(outterIdentifier[0].getText()))
            return false;
        PsiExpression[] arguments = x.getArgumentList().getExpressions();
        if (arguments == null || arguments.length != 1 || !iz.binaryExpression(arguments[0]))
            return false;
        PsiReferenceExpression[] innerReference = PsiTreeUtil.getChildrenOfType(
                az.methodCallExpression(az.binaryExpression(arguments[0]).getLOperand()).getMethodExpression(),
                PsiReferenceExpression.class);
        PsiIdentifier[] innerIdentifier = PsiTreeUtil.getChildrenOfType(
                az.methodCallExpression(az.binaryExpression(arguments[0]).getLOperand()).getMethodExpression(),
                PsiIdentifier.class);
        return innerIdentifier != null && innerReference != null
                && outterReference[0].getText().equals(innerReference[0].getText())
                && "size".equals(innerIdentifier[0].getText())
                && "1".equals(az.binaryExpression(arguments[0]).getROperand().getText())
                && "-".equals(az.binaryExpression(arguments[0]).getOperationSign().getText());
    }

    @Override
    public boolean canTip(PsiElement ¢) {
        return iz.methodCallExpression(¢) && canTip(az.methodCallExpression(¢));
    }

    @Override
    public String description(PsiMethodCallExpression ¢) {
        return "replace " + ¢.getText() + " with list last";
    }

    @Override
    public PsiElement createReplacement(PsiMethodCallExpression ¢) {
        return JavaPsiFacade.getElementFactory(¢.getProject()).createExpressionFromText("last("
                + PsiTreeUtil.getChildrenOfType(¢.getMethodExpression(), PsiReferenceExpression.class)[0].getText()
                + ")", ¢);
    }

    @Override
    protected Tip pattern(PsiMethodCallExpression ¢) {
        return tip(¢);
    }

    @Override
    public Class<PsiMethodCallExpression> getPsiClass() {
        return PsiMethodCallExpression.class;
    }
}

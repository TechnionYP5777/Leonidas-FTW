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

    private boolean canTip(PsiMethodCallExpression e) {
        // holds $x
        PsiReferenceExpression[] outterReference = PsiTreeUtil.getChildrenOfType(e.getMethodExpression(), PsiReferenceExpression.class);
        // holds "get"
        PsiIdentifier[] outterIdentifier = PsiTreeUtil.getChildrenOfType(e.getMethodExpression(), PsiIdentifier.class);
        //checks the call is to $x.get
        if (outterReference == null || outterIdentifier == null || outterReference.length != 1 || outterIdentifier.length != 1 || !outterIdentifier[0].getText().equals("get")) {
            return false;
        }

        // holds the arguments
        PsiExpression[] arguments = e.getArgumentList().getExpressions();
        // checks there is only one argument and it is a binary expression
        if (arguments == null || arguments.length != 1 || !iz.binaryExpression(arguments[0])) {
            return false;
        }

        //holds $x
        PsiReferenceExpression[] innerReference = PsiTreeUtil.getChildrenOfType(az.methodCallExpression(az.binaryExpression(arguments[0]).getLOperand()).getMethodExpression(), PsiReferenceExpression.class);
        //holds "size"
        PsiIdentifier[] innerIdentifier = PsiTreeUtil.getChildrenOfType(az.methodCallExpression(az.binaryExpression(arguments[0]).getLOperand()).getMethodExpression(), PsiIdentifier.class);
        // checks that the left operand of the binary expression is $x.size
        if (innerIdentifier == null || innerReference == null || !outterReference[0].getText().equals(innerReference[0].getText()) || !innerIdentifier[0].getText().equals("size")) {
            return false;
        }
        // checks minus 1
        return az.binaryExpression(arguments[0]).getROperand().getText().equals("1")
                && az.binaryExpression(arguments[0]).getOperationSign().getText().equals("-");
    }

    @Override
    public boolean canTip(PsiElement e) {
        return iz.methodCallExpression(e) && canTip(az.methodCallExpression(e));
    }

    @Override
    public String description(PsiMethodCallExpression psiMethodCallExpression) {
        return "replace " + psiMethodCallExpression.getText() + " with list last";
    }

    @Override
    public PsiElement createReplacement(PsiMethodCallExpression e) {
        PsiReferenceExpression container = PsiTreeUtil.getChildrenOfType(e.getMethodExpression(), PsiReferenceExpression.class)[0];
        return JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("last(" + container.getText() + ")", e);
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

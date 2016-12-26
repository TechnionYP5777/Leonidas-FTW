package plugin.tippers;

import auxilary_layer.PsiRewrite;
import auxilary_layer.Utils;
import auxilary_layer.az;
import auxilary_layer.iz;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

import java.util.LinkedList;

/**
 * @author AnnaBel7
 * @since 23/12/2016.
 */

public class LispLastElement implements Tipper<PsiMethodCallExpression> {

    private boolean canTip(PsiMethodCallExpression e) {
        // holds $x
        PsiReferenceExpression[] outterReference = PsiTreeUtil.getChildrenOfType(e.getMethodExpression(), PsiReferenceExpression.class);
        // holds "get"
        PsiIdentifier[] outterIdentifier = PsiTreeUtil.getChildrenOfType(e.getMethodExpression(), PsiIdentifier.class);
        //checks the call is to $x.get
        if (outterReference.length != 1 || outterIdentifier.length != 1 || !outterIdentifier[0].getText().equals("get")) {
            return false;
        }

        // holds the arguments
        PsiExpression[] arguments = e.getArgumentList().getExpressions();
        // checks there is only one argument and it is a binary expression
        if (arguments.length != 1 || !isBinaryExpression(arguments[0])) {
            return false;
        }

        //holds $x
        PsiReferenceExpression[] innerReference = PsiTreeUtil.getChildrenOfType(((PsiMethodCallExpression) ((PsiBinaryExpression) arguments[0]).getLOperand()).getMethodExpression(), PsiReferenceExpression.class);
        //holds "size"
        PsiIdentifier[] innerIdentifier = PsiTreeUtil.getChildrenOfType(((PsiMethodCallExpression) ((PsiBinaryExpression) arguments[0]).getLOperand()).getMethodExpression(), PsiIdentifier.class);
        // checks that the left operand of the binary expression is $x.size
        if (!outterReference[0].getText().equals(innerReference[0].getText()) || !innerIdentifier[0].getText().equals("size")) {
            return false;
        }
        // checks minus 1
        return ((PsiBinaryExpression) arguments[0]).getROperand().getText().equals("1")
                && ((PsiBinaryExpression) arguments[0]).getOperationSign().getText().equals("-");
    }

    LinkedList<Integer> l = new LinkedList<>();

    private Integer check() {
        return l.get(l.size() - 1);
    }

    @Override
    public boolean canTip(PsiElement e) {
        return isCallExpression(e) && canTip((PsiMethodCallExpression) e);
    }

    @Override
    public String description(PsiMethodCallExpression psiMethodCallExpression) {
        return "replace " + psiMethodCallExpression.getText() + " with lisp last";
    }

    @Override
    public Tip tip(PsiMethodCallExpression node) {
        return node == null || !canTip((PsiElement) node) ? null : new Tip(description(node), node, this.getClass()) {
            @Override
            public void go(PsiRewrite r) {
                PsiReferenceExpression container =PsiTreeUtil.getChildrenOfType(node.getMethodExpression(), PsiReferenceExpression.class)[0];

                String replacement = "last(" + container.getText() + ")";
                PsiExpression newNode = JavaPsiFacade.getElementFactory(node.getProject()).createExpressionFromText(replacement, node);

                String listType = container.getType().getCanonicalText();
                String listElementType = PsiUtil.extractIterableTypeParameter(container.getType(), false).getCanonicalText();

                PsiMethod last = (PsiMethod) PsiFileFactory.getInstance(node.getProject()).createFileFromText("LispLastElement.java", FileTypeRegistry.getInstance().getFileTypeByFileName("LispLastElement.java"), "public class Banana { static " + listElementType + " last(" + listType + " y) { return y.get(y.size() - 1); } }").getFirstChild().getNextSibling().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling();
                new WriteCommandAction.Simple(node.getProject(), node.getContainingFile()) {
                    @Override
                    protected void run() throws Throwable {
                        Utils.getCountainingClass(node).addBefore(last, Utils.getCountainingClass(node).getRBrace());
                        node.replace(newNode);
                    }
                }.execute();
            }
        };
    }

    @Override
    public Class<PsiMethodCallExpression> getPsiClass() {
        return PsiMethodCallExpression.class;
    }

    private boolean isCallExpression(PsiElement e) {
        return e instanceof PsiMethodCallExpression;
    }

    private boolean isBinaryExpression(PsiElement e) {
        return e instanceof PsiBinaryExpression;
    }
}

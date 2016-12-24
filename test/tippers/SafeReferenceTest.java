package tippers;

import auxilary_layer.az;
import com.intellij.psi.*;
import com.intellij.testFramework.PsiTestCase;

/**
 * Created by amirsagiv on 12/24/16.
 */
public class SafeReferenceTest extends PsiTestCase {

    public void test1() {
        PsiFile f = createDummyFile("banana.java", "class A{}");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x == null ? null : x.y", e);
        String y = az.referenceExpression(az.conditionalExpression(exp).getElseExpression()).getReferenceNameElement().getText();
        String x  = az.referenceExpression(az.conditionalExpression(exp).getElseExpression()).getQualifier().getText();
    }
}

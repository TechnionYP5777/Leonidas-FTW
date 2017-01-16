package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import il.org.spartan.ispartanizer.tippers.TipperTest;

public class typeTest extends TipperTest {
    public void testCallExpression() {
        PsiCallExpression e = createTestNewExpression("Object", "");
        assertEquals(type.of(e), PsiCallExpression.class);
    }

    public void testMethodReferenceExpression() {
        PsiMethodReferenceExpression e = createTestMethodReferenceEpression("Object", "toString");
        assertEquals(type.of(e), PsiMethodReferenceExpression.class);
    }

    public void testMethodCallExpression() {
        PsiMethodCallExpression e = createTestMethodCallExpression("banana", "5");
        assertEquals(type.of(e), PsiMethodCallExpression.class);
    }

    public void testMethod() {
        PsiMethod e = createTestMethodFromString("int foo() { return 5; }");
        assertEquals(type.of(e), PsiMethod.class);
    }

    public void testConditionalExpression() {
        PsiConditionalExpression e = createTestConditionalExpression("x > 0", "null", "x");
        assertEquals(type.of(e), PsiConditionalExpression.class);
    }

    public void testIdentifier() {
        PsiIdentifier e = createTestIdentifierFromString("banana");
        assertEquals(type.of(e), PsiIdentifier.class);
    }

    public void testForeachStatement() {
        PsiForeachStatement e = createTestForeachStatementFromString("for(Object x : list) { x.toString(); }");
        assertEquals(type.of(e), PsiForeachStatement.class);
    }

    public void testLambdaExpression() {
        PsiLambdaExpression e = createTestLambdaExpression("x -> x + 2");
        assertEquals(type.of(e), PsiLambdaExpression.class);
    }

    public void testIfStatement() {
        PsiIfStatement e = createTestIfStatement("x > 0", "return 5");
        assertEquals(type.of(e), PsiIfStatement.class);
    }

    public void testWhileStatement() {
        PsiWhileStatement e = createTestWhileStatementFromString("while(x > 0) { x--; }");
        assertEquals(type.of(e), PsiWhileStatement.class);
    }
}
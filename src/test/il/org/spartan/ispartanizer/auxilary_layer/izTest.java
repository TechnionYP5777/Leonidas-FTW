package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import il.org.spartan.ispartanizer.tippers.TipperTest;


/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class izTest extends TipperTest {
    public void testNull$() throws Exception {
        PsiLiteralExpression x = createTestNullExpression();
        assertTrue(iz.null$(x));
    }

    public void testNotNull() throws Exception {
        PsiLiteralExpression x = createTestNullExpression();
        assertFalse(iz.notNull(x));
        PsiExpression y = createTestExpressionFromString("x + 1");
        assertTrue(iz.notNull(y));
    }

    public void testStatement() throws Exception {
        PsiStatement s = createTestStatementFromString("return x + 1;");
        assertTrue(iz.statement(s));
        PsiCodeBlock x = createTestCodeBlockFromString("{ return x + 1; }");
        assertFalse(iz.statement(x));
    }

    public void testBlockStatement() throws Exception {

    }

    public void testBinaryExpression() throws Exception {

    }

    public void testReferenceExpression() throws Exception {

    }

    public void testEqualsOperator() throws Exception {

    }

    public void testNotEqualsOperator() throws Exception {

    }

    public void testLiteral() throws Exception {

    }

    public void testClassDeclaration() throws Exception {

    }

    public void testForStatement() throws Exception {

    }

    public void testForEachStatement() throws Exception {

    }

    public void testIfStatement() throws Exception {

    }

    public void testImportList() throws Exception {

    }

    public void testJavaToken() throws Exception {

    }

    public void testMethodCallExpression() throws Exception {

    }

    public void testTheSameType() throws Exception {
        PsiDeclarationStatement x = createTestDeclarationStatement("x", "Integer", "5 + 8");
        PsiStatement y = createTestStatementFromString("banana(5*x)");
        assertFalse(iz.theSameType(x, y));
        PsiStatement z = createTestStatementFromString("apple(x - 4)");
        assertTrue(iz.theSameType(y, z));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testBlock() throws Exception {
        PsiCodeBlock b = createTestCodeBlockFromString("{ int x = 5; }");
        assertTrue(iz.block(b));
        PsiStatement s = createTestStatementFromString("int x = 5;");
        assertFalse(iz.block(s));
    }

    public void testDeclarationStatement() throws Exception {

    }

    public void testEnumConstant() throws Exception {

    }

    public void testFieldDeclaration() throws Exception {

    }

    public void testAbstract$() throws Exception {

    }

    public void testStatic$() throws Exception {

    }

    public void testSingleParameterMethod() throws Exception {

    }

    public void testVoid$() throws Exception {

    }

    public void testPublic$() throws Exception {

    }

    public void testMain() throws Exception {

    }

    public void testReturnStatement() throws Exception {

    }

    public void testType() throws Exception {

    }

    public void testMethodInvocation() throws Exception {

    }

    public void testExpressionStatement() throws Exception {

    }

    public void testIdentifier() throws Exception {

    }

    public void testConditionalExpression() throws Exception {

    }

    public void testNullExpression() throws Exception {
        PsiFile f = createDummyFile("banana.java", "class A{ int foo(int x) { return x > 0 ? null : x; } }");
        PsiElement e = f.getNode().getPsi();
        final Wrapper<PsiConditionalExpression> w = new Wrapper<>();
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitConditionalExpression(PsiConditionalExpression expression) {
                super.visitConditionalExpression(expression);
                w.inner = expression;
            }
        });
        assert (iz.conditionalExpression(w.inner));
        assert (iz.nullExpression(w.inner.getThenExpression()));
    }
}
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
        PsiBinaryExpression eq= (PsiBinaryExpression) createTestExpression("x == y");
        assertTrue(iz.binaryExpression(eq));
        PsiBinaryExpression neq= (PsiBinaryExpression) createTestExpression("x != y");
        assertTrue(iz.binaryExpression(neq));
        PsiBinaryExpression bPlus= (PsiBinaryExpression) createTestExpression("x + y");
        assertTrue(iz.binaryExpression(bPlus));
        PsiBinaryExpression bMod= (PsiBinaryExpression) createTestExpression("x % y");
        assertTrue(iz.binaryExpression(bMod));
        PsiExpression notBinary = createTestExpression("!x");
        assertFalse(iz.binaryExpression(notBinary));
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
        PsiMethodCallExpression getterCall= (PsiMethodCallExpression) createTestExpression("getX()");
        assertTrue(iz.methodCallExpression(getterCall));
        PsiMethodCallExpression foo= (PsiMethodCallExpression) createTestExpression("foo(x,y)");
        assertTrue(iz.methodCallExpression(foo));
        PsiExpression listSize= createTestExpression("list.size()");
        assertTrue(iz.methodCallExpression(listSize));
        PsiExpression ifExp= createTestExpression("x+y");
        assertFalse(iz.methodCallExpression(ifExp));
        PsiElement fooSig= createTestMethodFromString("foo(int x,double y)");
        assertFalse(iz.methodCallExpression(fooSig));
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
        PsiDeclarationStatement integerDec = createTestDeclarationStatement("x", "Integer" , "7");
        assertTrue(iz.declarationStatement(integerDec));
        PsiDeclarationStatement objDec = createTestDeclarationStatement("x", "Object" , "null");
        assertTrue(iz.declarationStatement(objDec));
        PsiElement fooSig= createTestMethodFromString("foo(int x,double y)");
        assertFalse(iz.declarationStatement(fooSig));
    }

    public void testEnumConstant() throws Exception {
        PsiEnumConstant ec1 = createTestEnumFromString("ENUM_VALUE_ONE");
        assertTrue(iz.enumConstant(ec1));
        PsiEnumConstant ec2 = createTestEnumFromString("ENUM_VALUE_TWO(9)");
        assertTrue(iz.enumConstant(ec2));
        PsiElement fooSig= createTestMethodFromString("foo(int x,double y)");
        assertFalse(iz.enumConstant(fooSig));

    }

    public void testFieldDeclaration() throws Exception {
        PsiField f1 = createTestFieldDeclarationFromString("int x;");
        assertTrue(iz.fieldDeclaration(f1));
        PsiField f2 = createTestFieldDeclarationFromString("final int x = 9;");
        assertTrue(iz.fieldDeclaration(f2));
        PsiField f3 = createTestFieldDeclarationFromString("public static int x;");
        assertTrue(iz.fieldDeclaration(f3));
        PsiElement fooSig= createTestMethodFromString("foo(int x,double y)");
        assertFalse(iz.enumConstant(fooSig));
    }

    public void testAbstract$() throws Exception {
        PsiMethod m1 = createTestMethodFromString("public abstract void method(){}");
        assertTrue(iz.abstract$(m1));
        PsiMethod m2 = createTestMethodFromString("abstract public void method(int x);");
        assertTrue(iz.abstract$(m2));
        PsiMethod m3 = createTestMethodFromString("public void method(){}");
        assertFalse(iz.abstract$(m3));

    }

    public void testStatic$() throws Exception {
        PsiMethod m1 = createTestMethodFromString("public static void method(){}");
        assertTrue(iz.static$(m1));
        PsiMethod m2 = createTestMethodFromString("static abstract public void method(int x);");
        assertTrue(iz.static$(m2));
        PsiMethod m3 = createTestMethodFromString("public void method(){}");
        assertFalse(iz.static$(m3));
    }

    public void testSingleParameterMethod() throws Exception {
        PsiMethod m1 = createTestMethodFromString("public static void method(int x){}");
        assertTrue(iz.singleParameterMethod(m1));
        PsiMethod m2 = createTestMethodFromString("public static void method(int x, int y){}");
        assertFalse(iz.singleParameterMethod(m2));
        PsiMethod m3 = createTestMethodFromString("public void method(){}");
        assertFalse(iz.singleParameterMethod(m3));
    }

    public void testMethod() throws Exception{
        PsiElement e1 = createTestMethodFromString("public static void method(int x){}");
        assertTrue(iz.method(e1));
        PsiElement e2 = createTestMethodFromString("static abstract public void method(int x);");
        assertTrue(iz.method(e2));
        PsiElement e3 = createTestClassFromString("public class method(){}");
        assertFalse(iz.method(e3));
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
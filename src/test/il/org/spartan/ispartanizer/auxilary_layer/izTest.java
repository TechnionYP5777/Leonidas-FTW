package il.org.spartan.ispartanizer.auxilary_layer;


import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl;
import com.intellij.psi.impl.source.tree.java.PsiBinaryExpressionImpl;
import com.intellij.psi.impl.source.tree.java.PsiConditionalExpressionImpl;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsiExpression;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsiStatement;
import il.org.spartan.ispartanizer.tippers.TipperTest;



/**
 * @author michal cohen & Amir Sagiv
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
        PsiExpression rf1 = createTestExpression("x.y");
        assertTrue(iz.referenceExpression(rf1));
        PsiExpression rf2 = createTestExpression("x.y.z");
        assertTrue(iz.referenceExpression(rf2));
        PsiExpression rf3 = createTestExpression("x");
        assertTrue(iz.referenceExpression(rf3));
        PsiExpression exp = createTestExpression("x == false");
        assertFalse(iz.referenceExpression(exp));
    }

    public void testEqualsOperator() throws Exception {
        PsiJavaToken t1 = ((PsiBinaryExpression)createTestExpression("x == y")).getOperationSign();
        assertTrue(iz.equalsOperator(t1.getTokenType()));
        PsiJavaToken t2 = ((PsiBinaryExpression)createTestExpression("x != y")).getOperationSign();
        assertFalse(iz.equalsOperator(t2.getTokenType()));
        PsiJavaToken t3 = ((PsiBinaryExpression)createTestExpression("x >= y")).getOperationSign();
        assertFalse(iz.equalsOperator(t3.getTokenType()));
        PsiJavaToken t4 = ((PsiBinaryExpression)createTestExpression("x + y")).getOperationSign();
        assertFalse(iz.equalsOperator(t4.getTokenType()));
    }

    public void testNotEqualsOperator() throws Exception {
        PsiJavaToken t1 = ((PsiBinaryExpression)createTestExpression("x != y")).getOperationSign();
        assertTrue(iz.notEqualsOperator(t1.getTokenType()));
        PsiJavaToken t2 = ((PsiBinaryExpression)createTestExpression("x == y")).getOperationSign();
        assertFalse(iz.notEqualsOperator(t2.getTokenType()));
        PsiJavaToken t3 = ((PsiBinaryExpression)createTestExpression("x >= y")).getOperationSign();
        assertFalse(iz.notEqualsOperator(t3.getTokenType()));
        PsiJavaToken t4 = ((PsiBinaryExpression)createTestExpression("x + y")).getOperationSign();
        assertFalse(iz.notEqualsOperator(t4.getTokenType()));
    }

    public void testLiteral() throws Exception {
        PsiExpression l1 = createTestExpression("null");
        assertTrue(iz.literal(l1));
        PsiExpression l2 = createTestExpression("false");
        assertTrue(iz.literal(l2));
        PsiExpression l3 = createTestExpression("b == false");
        assertFalse(iz.literal(l3));
    }

    public void testClassDeclaration() throws Exception {
        PsiElement c1 = createTestClassFromString("public class A{}");
        assertTrue(iz.classDeclaration(c1));
        PsiElement c2 = createTestClassFromString("private class A{}");
        assertTrue(iz.classDeclaration(c2));
        PsiElement i1 = createTestClassFromString("public interface A{}");
        assertTrue(iz.classDeclaration(i1));
        PsiElement c4 = createTestClassFromString("public abstract class A{}");
        assertTrue(iz.classDeclaration(c4));
    }

    public void testForStatement() throws Exception {
        PsiElement e1= createTestForStatementFromString("for(int i =0 ; i< 10 ; i++){}");
        assertTrue(iz.forStatement(e1));
        PsiElement e2= createTestForStatementFromString("for(int i =0 ; i< i+1 ; i--){}");
        assertTrue(iz.forStatement(e2));
        PsiElement e3= createTestStatementFromString("for(int x : array){}");
        assertFalse(iz.forStatement(e3));

    }

    public void testForEachStatement() throws Exception {
        PsiElement e1= createTestForeachStatementFromString("for(int i : list){}");
        assertTrue(iz.forEachStatement(e1));
        PsiElement e2= createTestForeachStatementFromString("for(Object o : new ArrayList<Object>()){}");
        assertTrue(iz.forEachStatement(e2));
        PsiElement e3= createTestForStatementFromString("for(int i = 0 ; i<11 ; i++){}");
        assertFalse(iz.forEachStatement(e3));
    }

    public void testIfStatement() throws Exception {
        PsiElement if1 = createTestIfStatement("x > 2", "break;");
        assertTrue(iz.ifStatement(if1));
        PsiElement if2 = createTestIfStatement("x == 2", "continue;");
        assertTrue(iz.ifStatement(if2));
        PsiElement notIf = createTestForStatementFromString("for(int i = 0 ; i < 5 ; i++){}");
        assertFalse(iz.ifStatement(notIf));

    }

    public void testImportList() throws Exception {
        PsiElement importList = createTestImportListFromString("import java.util.*;"+
                                                                  "import sparta.boom;");
        assertTrue(iz.importList(importList));
        PsiElement notImportList = createTestForStatementFromString("for(int i = 0 ; i < 5 ; i++){}");
        assertFalse(iz.ifStatement(notImportList));
    }

    public void testJavaToken() throws Exception {
        PsiJavaToken t1 = ((PsiBinaryExpression)createTestExpression("x == y")).getOperationSign();
        assertTrue(iz.javaToken(t1));
        PsiJavaToken t2 = ((PsiBinaryExpression)createTestExpression("x != y")).getOperationSign();
        assertTrue(iz.javaToken(t2));
        PsiElement notToken = createTestForStatementFromString("for(int i = 0 ; i < 5 ; i++){}");
        assertFalse(iz.ifStatement(notToken));

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
        assertFalse(iz.fieldDeclaration(fooSig));
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
        PsiMethod m1 = createTestMethodFromString("public static void method(int x){}");
        assertTrue(iz.void$(m1));
        PsiMethod m2 = createTestMethodFromString("protected void method(){}");
        assertTrue(iz.void$(m2));
        PsiMethod m3 = createTestMethodFromString("public static boolean method(){return true;}");
        assertFalse(iz.void$(m3));
    }

    public void testPublic$() throws Exception {
        PsiMethod m1 = createTestMethodFromString("public static void method(int x){}");
        assertTrue(iz.public$(m1));
        PsiMethod m2 = createTestMethodFromString("protected void method(){}");
        assertFalse(iz.public$(m2));
        PsiMethod m3 = createTestMethodFromString("private static boolean method(){return true;}");
        assertFalse(iz.public$(m3));
        PsiMethod m4 = createTestMethodFromString("void method(){}");
        assertFalse(iz.public$(m4));
    }

    public void testMain() throws Exception {
        PsiMethod m1 = createTestMethodFromString("public static void main(String [] args){}");
        assertTrue(iz.main(m1));
        PsiMethod m2 = createTestMethodFromString("public static void method(String [] args){}");
        assertFalse(iz.main(m2));
        PsiMethod m3 = createTestMethodFromString("private static void main(String [] args){}");
        assertFalse(iz.main(m3));
        PsiMethod m4 = createTestMethodFromString("protected static void main(String [] args){}");
        assertFalse(iz.main(m4));
        PsiMethod m5 = createTestMethodFromString("public static void main(String [] arg){}");
        assertFalse(iz.main(m5));
        PsiMethod m6 = createTestMethodFromString("public void main(String [] args){}");
        assertFalse(iz.main(m6));
    }

    public void testReturnStatement() throws Exception {
        PsiStatement rt1 = createTestStatementFromString("return x");
        assertTrue(iz.returnStatement(rt1));
        PsiStatement rt2 = createTestStatementFromString("return x.y");
        assertTrue(iz.returnStatement(rt2));
        PsiStatement rt3 = createTestStatementFromString("return x == null ? y : x");
        assertTrue(iz.returnStatement(rt3));
        PsiStatement s = createTestStatementFromString("int x;");
        assertFalse(iz.returnStatement(s));

    }

    public void testType() throws Exception {
        PsiElement te1 = createTestTypeElementFromString("int");
        assertTrue(iz.type(te1));
        PsiElement te2 = createTestTypeElementFromString("Integer");
        assertTrue(iz.type(te2));
        PsiElement te3 = createTestTypeElementFromString("Object");
        assertTrue(iz.type(te3));
        PsiElement te4 = createTestTypeElementFromString("List<T>");
        assertTrue(iz.type(te4));
        PsiElement e = createTestStatementFromString("int x;");
        assertFalse(iz.type(e));

    }


    public void testExpressionStatement() throws Exception {
        PsiStatement s1 = createTestStatementFromString("2+3");
        assertTrue(iz.expressionStatement(s1));
        PsiStatement s2 = createTestStatementFromString("true");
        assertTrue(iz.expressionStatement(s2));
        PsiStatement s3 = createTestStatementFromString("int x = 2+3");
        assertFalse(iz.expressionStatement(s3));
    }

    public void testIdentifier() throws Exception {
        PsiIdentifier id1 = createTestIdentifierFromString("x");
        assertTrue(iz.identifier(id1));
        PsiIdentifier id2 = createTestIdentifierFromString("_");
        assertTrue(iz.identifier(id2));
        PsiIdentifier id3 = createTestIdentifierFromString("$");
        assertTrue(iz.identifier(id3));
        PsiElement e = createTestStatementFromString("int x;");
        assertFalse(iz.identifier(e));
    }

    public void testConditionalExpression() throws Exception {
        PsiConditionalExpression c1= createTestConditionalExpression("x == null" , "x = true" , "null");
        assertTrue(iz.conditionalExpression(c1));
        PsiConditionalExpression c2= createTestConditionalExpression("x != null" , "x = true" ,null);
        assertTrue(iz.conditionalExpression(c2));
        PsiElement e = createTestStatementFromString("int x;");
        assertFalse(iz.identifier(e));
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

    public void testExpression() throws Exception{
        PsiElement e1 = createTestExpression("x == y");
        assertTrue(iz.expression(e1));
        PsiElement e2 = createTestExpression("3 >= y");
        assertTrue(iz.expression(e2));
        PsiElement e = createTestStatementFromString("int x;");
        assertFalse(iz.expression(e));
    }

    public void testWhitespace() throws Exception{
        PsiWhiteSpace ws = new PsiWhiteSpaceImpl("");
        assertTrue(iz.whiteSpace(ws));
        PsiElement e = createTestStatementFromString("int x;");
        assertFalse(iz.whiteSpace(e));
    }

    public void testOfType() throws Exception{
        PsiWhiteSpace ws = new PsiWhiteSpaceImpl("");
        assertTrue(iz.ofType(ws,PsiWhiteSpaceImpl.class));
        PsiElement e1 = createTestExpression("x == y");
        assertTrue(iz.ofType(e1, PsiBinaryExpressionImpl.class));
        PsiConditionalExpression c1= createTestConditionalExpression("x == null" , "x = true" , "null");
        assertTrue(iz.ofType(c1, PsiConditionalExpressionImpl.class));
    }

    public void testDocumentedElement() throws Exception{
        PsiElement e = createTestClassFromString("/**\n" +
                " * This is a main class JavaDoc\n" +
                " */\n" +
                "public class Main {}");
        assertTrue(iz.documentedElement(e));
        PsiElement e2 = createTestClassFromString("public class Main {}");
        assertTrue(iz.documentedElement(e2));
        PsiMethod m =  createTestMethodFromString("public int getX(){return x;}");
        assertTrue(iz.documentedElement(m));
        PsiMethodCallExpression mc = createTestMethodCallExpression("foo","a");
        assertFalse(iz.documentedElement(mc));
    }

    public void testjavaDoc() throws Exception{
        PsiElement e = createTestDocCommentFromString("/**\n" +
                " * This is a main class JavaDoc\n" +
                " */\n");
        assertTrue(iz.javadoc(e));
        PsiElement e2 = createTestCommentFromString("//comment");
        assertFalse(iz.javadoc(e2));
        PsiElement e3 = createTestCommentFromString("/*comment*/");
        assertFalse(iz.javadoc(e3));
    }

    public void testWhileStatement() throws Exception{
        PsiElement e1 = createTestWhileStatementFromString("while(true){}");
        assertTrue(iz.whileStatement(e1));
        PsiElement e2 = createTestDoWhileStatementFromString("do{}while(true)");
        assertFalse(iz.whileStatement(e2));
        PsiElement e3 = createTestForStatementFromString("for(int i = 0 ; i< 5 ; i++){}");
        assertFalse(iz.whileStatement(e3));
    }

    public void testSwitchStatement() throws Exception{
        PsiElement e1 = createTestSwitchStatementFromString("switch(x){ case 1: break; case 2: break;}");
        assertTrue(iz.switchStatement(e1));
        PsiElement e2 = createTestSwitchStatementFromString("switch(x){ case 1: break; case 2: break; default: continue;}");
        assertTrue(iz.switchStatement(e2));
        PsiElement e3 = createTestForStatementFromString("for(int i = 0 ; i< 5 ; i++){}");
        assertFalse(iz.switchStatement(e3));

    }

    public void testEnclosingStatement() throws Exception{
        PsiElement e1 = createTestSwitchStatementFromString("switch(x){ case 1: break; case 2: break;}");
        assertTrue(iz.enclosingStatement(e1));
        PsiElement e2 = createTestWhileStatementFromString("while(true){}");
        assertTrue(iz.enclosingStatement(e2));
        PsiElement e3= createTestForStatementFromString("for(int i =0 ; i< 10 ; i++){}");
        assertTrue(iz.enclosingStatement(e3));
        PsiElement e4= createTestForeachStatementFromString("for(int i : list){}");
        assertTrue(iz.enclosingStatement(e4));
        PsiElement e5 = createTestIfStatement("x > 2", "break;");
        assertTrue(iz.enclosingStatement(e5));
        PsiElement e6 = createTestStatementFromString("int x;");
        assertFalse(iz.enclosingStatement(e6));
    }

    public void testSynchronized() throws Exception{
        PsiMethod e1 = createTestMethodFromString("synchronized public int getX(){return x;}");
        assertTrue(iz.synchronized¢(e1));
        PsiMethod e2 = createTestMethodFromString("public int getX(){return x;}");
        assertFalse(iz.synchronized¢(e2));
    }

    public void testNative() throws Exception{
        PsiMethod e1 = createTestMethodFromString("native public int getX(){return x;}");
        assertTrue(iz.native¢(e1));
        PsiMethod e2 = createTestMethodFromString("public int getX(){return x;}");
        assertFalse(iz.native¢(e2));
    }

    public void testFinalMember() throws Exception{
        PsiMember e1 = createTestFieldDeclarationFromString("final private int x");
        assertTrue(iz.final¢(e1));
        PsiMember e2 = createTestFieldDeclarationFromString("private int x");
        assertFalse(iz.final¢(e2));
    }

    public void testFinalVariable() throws Exception{
        PsiVariable e1 = createTestFieldDeclarationFromString("final private int x");
        assertTrue(iz.final¢(e1));
        PsiVariable e2 = createTestFieldDeclarationFromString("private int x");
        assertFalse(iz.final¢(e2));
    }

    public void testDefault() throws Exception{
        PsiMethod e1 = createTestMethodFromString("default public int getX(){return x;}");
        assertTrue(iz.default¢(e1));
        PsiMethod e2 = createTestMethodFromString("public int getX(){return x;}");
        assertFalse(iz.default¢(e2));
    }

    public void testAnnotation() throws Exception{
        PsiElement e1 = createTestAnnotationFromString("@annotation");
        assertTrue(iz.annotation(e1));
        PsiMethod e2 = createTestMethodFromString("default public int getX(){@Wrong return x;}");
        assertFalse(iz.annotation(e2));
    }

    public void testArrayInitializer() throws Exception{
        PsiElement e1 = createTestExpression("{0,1,2}");
        assertTrue(iz.arrayInitializer(e1));
        PsiElement e2 = createTestExpression("{0}");
        assertTrue(iz.arrayInitializer(e2));
        PsiElement e3 = createTestExpression("{}");
        assertTrue(iz.arrayInitializer(e3));
        PsiElement e4 = createTestExpression("0");
        assertFalse(iz.arrayInitializer(e4));
    }

    public void testAssignmentExpression() throws Exception{
        PsiElement e1 = createTestExpression("x=5");
        assertTrue(iz.assignment(e1));
        PsiElement e2 = createTestExpression("x=++y");
        assertTrue(iz.assignment(e2));
        PsiElement e3 = createTestExpression("x>=5");
        assertFalse(iz.assignment(e3));
    }

    public void testBreakStatement() throws Exception{
        PsiElement e1 = createTestStatementFromString("break;");
        assertTrue(iz.breakStatement(e1));
        PsiElement e2 = createTestStatementFromString("continue;");
        assertFalse(iz.breakStatement(e2));
        PsiElement e3 = createTestStatementFromString("i++;");
        assertFalse(iz.breakStatement(e3));
    }

    public void testCastExpression() throws Exception{
        PsiElement e1 = createTestExpression("(double)x");
        assertTrue(iz.castExpression(e1));
        PsiElement e2 = createTestExpression("(Object)x");
        assertTrue(iz.castExpression(e2));
        PsiElement e3 = createTestExpression("(2 * x) * x");
        assertFalse(iz.castExpression(e3));
    }

    public void testClassInstanceCreation() throws Exception{
        PsiElement e1 = createTestExpression("new ArrayList()");
        assertTrue(iz.classInstanceCreation(e1));
    }

    public void testBooleanLiteral() throws Exception{
        PsiElement e1 = createTestExpression("true");
        assertTrue(iz.booleanLiteral(e1));
        PsiElement e2 = createTestExpression("false");
        assertTrue(iz.booleanLiteral(e2));
        PsiElement e3 = createTestExpression("null");
        assertFalse(iz.booleanLiteral(e3));
    }

    public void testBooleanType() throws Exception{
        PsiType e1 = createTestTypeFromString("boolean");
        assertTrue(iz.booleanType(e1));
        PsiType e3 = createTestTypeFromString("int");
        assertFalse(iz.booleanType(e3));
    }

    public void testConditionalAnd() throws Exception{
        PsiBinaryExpression e1 = (PsiBinaryExpression)createTestExpression("x && y");
        assertTrue(iz.conditionalAnd(e1));
        PsiBinaryExpression e2 = (PsiBinaryExpression)createTestExpression("x || y");
        assertFalse(iz.conditionalAnd(e2));
    }

    public void testConditionalOr() throws Exception{
        PsiBinaryExpression e1 = (PsiBinaryExpression)createTestExpression("x || y");
        assertTrue(iz.conditionalOr(e1));
        PsiBinaryExpression e2 = (PsiBinaryExpression)createTestExpression("x && y");
        assertFalse(iz.conditionalOr(e2));
    }

    public void testEmptyStatement() throws Exception{
        PsiElement e1 = createTestStatementFromString(";");
        assertTrue(iz.emptyStatement(e1));
        PsiElement e3 = createTestStatementFromString("int x;");
        assertFalse(iz.emptyStatement(e3));
    }

    public void testEnumDeclaration() throws Exception{
        PsiElement e1 = createEnumClassFromString("Day");
        assertTrue(iz.enumDeclaration(e1));
        PsiElement e2 = createTestClassFromString("public class Day{}");
        assertFalse(iz.enumDeclaration(e2));
    }

    public void testInterface() throws Exception{
        PsiElement e1 = createInterfaceClassFromString("A");
        assertTrue(iz.interface¢(e1));
        PsiElement e2 = createEnumClassFromString("A");
        assertFalse(iz.interface¢(e2));
    }

    public void testStubMethodCall() throws Exception{
        PsiElement e1 = createTestMethodCallExpression("booleanExpression");
        assertTrue(iz.stubMethodCall(e1));
        PsiElement e2 = createTestMethodCallExpression("foo");
        assertFalse(iz.stubMethodCall(e2));
    }

    public void testConforms() throws Exception{
        PsiElement e1 = (PsiBinaryExpression)createTestExpression("1+5");
        PsiElement e2 = (PsiBinaryExpression)createTestExpression("1 > 5");
        assertTrue(iz.conforms(e1,e2));
        GenericPsiExpression ge = new GenericPsiExpression(PsiType.BOOLEAN,e2);
        assertTrue(iz.conforms(e1,ge));
        assertFalse(iz.conforms(ge,e1));
        PsiElement e3 = createTestStatementFromString("return x");
        PsiElement e4 = createTestStatementFromString("return y");
        assertTrue(iz.conforms(e3,e4));
        GenericPsiStatement gs = new GenericPsiStatement(e4);
        assertTrue(iz.conforms(e3,gs));
        assertFalse(iz.conforms(gs,e3));
    }

}
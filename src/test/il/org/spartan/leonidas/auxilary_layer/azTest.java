package il.org.spartan.leonidas.auxilary_layer;

import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiStatement;
import il.org.spartan.leonidas.PsiTypeHelper;

/**
 * @author Amir Sagiv
 * @since 13/01/2017.
 */
public class azTest extends PsiTypeHelper {

    public static boolean typeCheck(Class<? extends PsiElement> type, PsiElement element) {
        return element != null && type.isAssignableFrom(element.getClass());
    }

    public void testAzStatement() throws Exception{
        PsiElement e1 = createTestStatementFromString("int x");
        assertNotNull(az.statement(e1));
        PsiElement e2 = createTestStatementFromString("return true;");
        assertNotNull(az.statement(e2));
        PsiElement e3 = createTestExpressionFromString("x== null ? null : x");
        assertNull(az.statement(e3));
    }

    public void testAzCodeBlock() throws Exception{
        PsiElement e1 = createTestCodeBlockFromString("{ int x = 5; }");
        assertNotNull(az.block(e1));
        PsiElement e2 = createTestStatementFromString("int x = 5;");
        assertNull(az.block(e2));
    }

    public void testAzDeclarationStatement() throws Exception{
        PsiElement integerDec = createTestDeclarationStatement("x", "Integer" , "7");
        assertNotNull(az.declarationStatement(integerDec));
        PsiElement objDec = createTestDeclarationStatement("x", "Object" , "null");
        assertNotNull(az.declarationStatement(objDec));
        PsiElement fooSig= createTestMethodFromString("foo(int x,double y)");
        assertNull(az.declarationStatement(fooSig));
    }

    public void testAzEnumConstant() throws Exception{
        PsiElement ec1 = createTestEnumFromString("ENUM_VALUE_ONE");
        assertNotNull(az.enumConstant(ec1));
        PsiElement ec2 = createTestEnumFromString("ENUM_VALUE_TWO(9)");
        assertNotNull(az.enumConstant(ec2));
        PsiElement fooSig= createTestMethodFromString("foo(int x,double y)");
        assertNull(az.enumConstant(fooSig));
    }

    public void testAzFieldDeclaration() throws Exception{
        PsiElement f1 = createTestFieldDeclarationFromString("int x;");
        assertNotNull(az.fieldDeclaration(f1));
        PsiElement f2 = createTestFieldDeclarationFromString("final int x = 9;");
        assertNotNull(az.fieldDeclaration(f2));
        PsiElement f3 = createTestFieldDeclarationFromString("public static int x;");
        assertNotNull(az.fieldDeclaration(f3));
        PsiElement fooSig= createTestMethodFromString("foo(int x,double y)");
        assertNull(az.fieldDeclaration(fooSig));
    }

    public void testAzExpressionStatement() throws Exception {
        PsiElement s1 = createTestStatementFromString("2+3");
        assertNotNull(az.expressionStatement(s1));
        PsiElement s2 = createTestStatementFromString("true");
        assertNotNull(az.expressionStatement(s2));
        PsiElement s3 = createTestStatementFromString("int x = 2+3");
        assertNull(az.expressionStatement(s3));
    }

    public void testAzMethodCallExpression() throws Exception{
        PsiElement getterCall = createTestExpression("getX()");
        assertNotNull(az.methodCallExpression(getterCall));
        PsiElement foo = createTestExpression("foo(x,y)");
        assertNotNull(az.methodCallExpression(foo));
        PsiElement listSize= createTestExpression("list.size()");
        assertNotNull(az.methodCallExpression(listSize));
        PsiElement ifExp= createTestExpression("x+y");
        assertNull(az.methodCallExpression(ifExp));
        PsiElement fooSig= createTestMethodFromString("foo(int x,double y)");
        assertNull(az.methodCallExpression(fooSig));
    }

    public void testAzIdentifier() throws Exception {
        PsiElement id1 = createTestIdentifierFromString("x");
        assertNotNull(az.identifier(id1));
        PsiElement id2 = createTestIdentifierFromString("_");
        assertNotNull(az.identifier(id2));
        PsiElement id3 = createTestIdentifierFromString("$");
        assertNotNull(az.identifier(id3));
        PsiElement e = createTestStatementFromString("int x;");
        assertNull(az.identifier(e));
    }

    public void testAzConditionalExpression() throws Exception {
        PsiElement c1= createTestConditionalExpression("x == null" , "x = true" , "null");
        assertNotNull(az.conditionalExpression(c1));
        PsiElement c2= createTestConditionalExpression("x != null" , "x = true" ,null);
        assertNotNull(az.conditionalExpression(c2));
        PsiElement e = createTestStatementFromString("int x;");
        assertNull(az.conditionalExpression(e));
    }

    public void testAzBinaryExpression() throws Exception {
        PsiElement eq = createTestExpression("x == y");
        assertNotNull(az.binaryExpression(eq));
        PsiElement neq = createTestExpression("x != y");
        assertNotNull(az.binaryExpression(neq));
        PsiElement bPlus = createTestExpression("x + y");
        assertNotNull(az.binaryExpression(bPlus));
        PsiElement bMod = createTestExpression("x % y");
        assertNotNull(az.binaryExpression(bMod));
        PsiElement notBinary = createTestExpression("!x");
        assertNull(az.binaryExpression(notBinary));
    }

    public void testAzReferenceExpression() throws Exception {
        PsiExpression rf1 = createTestExpression("x.y");
        assertNotNull(az.referenceExpression(rf1));
        PsiExpression rf2 = createTestExpression("x.y.z");
        assertNotNull(az.referenceExpression(rf2));
        PsiExpression rf3 = createTestExpression("x");
        assertNotNull(az.referenceExpression(rf3));
        PsiExpression exp = createTestExpression("x == false");
        assertNull(az.referenceExpression(exp));
    }

    public void testAzLiteral() throws Exception {
        PsiElement l1 = createTestExpression("null");
        assertNotNull(az.literal(l1));
        PsiElement l2 = createTestExpression("false");
        assertNotNull(az.literal(l2));
        PsiElement l3 = createTestExpression("b == false");
        assertNull(az.literal(l3));
    }

    public void testAzClassDeclaration() throws Exception {
        PsiElement c1 = createTestClassFromString("", "A", "", "public");
        assertNotNull(az.classDeclaration(c1));
        PsiElement c2 = createTestClassFromString("", "A", "", "private");
        assertNotNull(az.classDeclaration(c2));
        PsiElement i1 = createTestInterfaceFromString("", "A", "", "public");
        assertNotNull(az.classDeclaration(i1));
        PsiElement c4 = createTestClassFromString("", "A", "", "public", "abstract");
        assertNotNull(az.classDeclaration(c4));
    }

    public void testAzForEachStatement() throws Exception {
        PsiElement e1= createTestForeachStatementFromString("for(int i : list){}");
        assertNotNull(az.forEachStatement(e1));
        PsiElement e2= createTestForeachStatementFromString("for(Object o : new ArrayList<Object>()){}");
        assertNotNull(az.forEachStatement(e2));
        PsiElement e3= createTestForStatementFromString("for(int i = 0 ; i<11 ; i++){}");
        assertNull(az.forEachStatement(e3));
    }

    public void testIfStatement() throws Exception {
        PsiElement if1 = createTestIfStatement("x > 2", "break;");
        assertNotNull(az.ifStatement(if1));
        PsiElement if2 = createTestIfStatement("x == 2", "continue;");
        assertNotNull(az.ifStatement(if2));
        PsiElement notIf = createTestForStatementFromString("for(int i = 0 ; i < 5 ; i++){}");
        assertNull(az.ifStatement(notIf));

    }

    public void testAzReturnStatement() throws Exception {
        PsiStatement rt1 = createTestStatementFromString("return x");
        assertNotNull(az.returnStatement(rt1));
        PsiStatement rt2 = createTestStatementFromString("return x.y");
        assertNotNull(az.returnStatement(rt2));
        PsiStatement rt3 = createTestStatementFromString("return x == null ? y : x");
        assertNotNull(az.returnStatement(rt3));
        PsiStatement s = createTestStatementFromString("int x;");
        assertNull(az.returnStatement(s));
        assertNull(az.returnStatement(s));
    }

    public void testAzImportList() throws Exception {
        PsiElement importList = createTestImportListFromString("import java.util.*;"+
                "import sparta.boom;");
        assertNotNull(az.importList(importList));
        PsiElement notImportList = createTestForStatementFromString("for(int i = 0 ; i < 5 ; i++){}");
        assertNull(az.ifStatement(notImportList));
    }

    public void testAzJavaToken() throws Exception {
        PsiElement t1 = ((PsiBinaryExpression)createTestExpression("x == y")).getOperationSign();
        assertNotNull(az.javaToken(t1));
        PsiElement t2 = ((PsiBinaryExpression)createTestExpression("x != y")).getOperationSign();
        assertNotNull(az.javaToken(t2));
        PsiElement notToken = createTestForStatementFromString("for(int i = 0 ; i < 5 ; i++){}");
        assertNull(az.javaToken(notToken));
    }

    public void testAzMethod() throws Exception{
        PsiElement e1 = createTestMethodFromString("public static void method(int x){}");
        assertNotNull(az.method(e1));
        PsiElement e2 = createTestMethodFromString("static abstract public void method(int x);");
        assertNotNull(az.method(e2));
        PsiElement e3 = createTestClassFromString("", "method()", "", "public");
        assertNull(az.method(e3));
    }

    public void testAzBlockStatement() throws Exception {
        PsiElement b = createTestBlockStatementFromString("{ int x = 5; }");
        assertNotNull(az.blockStatement(b));
        PsiElement s = createTestStatementFromString("int x = 5;");
        assertNull(az.blockStatement(s));
    }

}
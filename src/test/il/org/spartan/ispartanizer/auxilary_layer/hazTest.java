package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.tippers.TipperTest;

/**
 * @author  Amir Sagiv
 * @since 17/01/2017.
 */
public class hazTest extends TipperTest {

    public void testHazCentVariableDefinition() throws Exception {
        PsiElement e1 = createTestStatementFromString("int ¢ = 5;");
        assertTrue(haz.centVariableDefinition(e1));
        PsiElement e2 = createTestBlockStatementFromString("{int x = 5; int ¢ = 5;}");
        assertTrue(haz.centVariableDefinition(e2));
        PsiElement e3 = createTestStatementFromString("int x = 5;");
        assertFalse(haz.centVariableDefinition(e3));
        PsiElement e4 = createTestMethodFromString("public static int getSomething(){int ¢ = 5; return ¢;}");
        assertTrue(haz.centVariableDefinition(e4));
        assertTrue(haz.centVariableDefinition(createTestClassFromString("", "A", "private int ¢;", "public")));
    }

    public void testHazFunctionNamed() throws Exception{
        PsiElement e1 = createTestMethodFromString("public int getX(){return 1;}");
        assertTrue(haz.functionNamed(e1,"getX"));
        PsiElement e2 = createTestClassFromString("","A","pubic A(){} private static int getX(){return 1;}","public");
        assertTrue(haz.functionNamed(e2,"getX"));
        PsiElement e3 = createTestClassFromString("","A","pubic A(){} private static int getY(){return 1;}","public");
        assertFalse(haz.functionNamed(e3,"getX"));
        assertTrue(haz.functionNamed(createTestInterfaceFromString("", "A", "private static int getX();", "public"),
                "getX"));

    }

    public void testHazEqualsOperator() throws Exception{
        PsiBinaryExpression e1 = (PsiBinaryExpression) createTestExpression("x == y");
        assertTrue(haz.equalsOperator(e1));
        PsiBinaryExpression e2 = (PsiBinaryExpression) createTestExpression("x != y");
        assertFalse(haz.equalsOperator(e2));
        assertFalse(haz.equalsOperator(((PsiBinaryExpression) createTestExpression("x > y"))));
    }

    public void testHazNotEqualsOperator() throws Exception{
        PsiBinaryExpression e1 = (PsiBinaryExpression) createTestExpression("x == y");
        assertFalse(haz.notEqualsOperator(e1));
        PsiBinaryExpression e2 = (PsiBinaryExpression) createTestExpression("x != y");
        assertTrue(haz.notEqualsOperator(e2));
        assertFalse(haz.notEqualsOperator(((PsiBinaryExpression) createTestExpression("x > y"))));
    }

//    public void testHazSyntaxErrors() throws Exception{
//        PsiBinaryExpression e1 = (PsiBinaryExpression) createTestExpression("x == y");
//        e1.deleteChildRange(e1.getLOperand(),e1.getLOperand());
//        assertTrue(haz.syntaxErrors(e1));
//    }

}
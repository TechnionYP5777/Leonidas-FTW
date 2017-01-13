package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import il.org.spartan.ispartanizer.tippers.TipperTest;

/**
 * @author Amir Sagiv
 * @since 13/01/2017.
 */
public class azTest extends TipperTest {

    public static boolean typeCheck(Class<? extends PsiElement> type, PsiElement element) {
        return element != null && type.isAssignableFrom(element.getClass());
    }

    public void testAzStatement() throws Exception{
        PsiElement e1 = createTestStatementFromString("int x");
        assertTrue(typeCheck(PsiStatement.class,e1));
        PsiElement e2 = createTestStatementFromString("return true;");
        assertTrue(typeCheck(PsiStatement.class,e2));
        PsiElement e3 = createTestExpressionFromString("x== null ? null : x");
        assertFalse(typeCheck(PsiStatement.class,e3));
    }

    public void testAzCodeBlock() throws Exception{
        PsiElement e1 = createTestCodeBlockFromString("{ int x = 5; }");
        assertTrue(typeCheck(PsiCodeBlock.class, e1));
        PsiElement e2 = createTestStatementFromString("int x = 5;");
        assertFalse(typeCheck(PsiCodeBlock.class,e2));
    }

}

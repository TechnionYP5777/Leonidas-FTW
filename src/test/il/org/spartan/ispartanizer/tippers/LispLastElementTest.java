package il.org.spartan.ispartanizer.tippers;

import com.intellij.psi.PsiMethodCallExpression;
import il.org.spartan.ispartanizer.PsiTypeHelper;
import il.org.spartan.ispartanizer.plugin.tippers.LispLastElement;

/**
 * @author AnnaBel7
 * @since 12/01/2017.
 */
public class LispLastElementTest extends PsiTypeHelper {
    public void testCanTipLegal() {
        assertTrue(new LispLastElement().canTip(createTestExpressionFromString("l.get(l.size()-1)")));
    }

    public void testCanTipIllegal1() {
        assertFalse(new LispLastElement().canTip(createTestExpressionFromString("x.get(y.size()-1)")));
    }

    public void testCanTipIllegal2() {
        assertFalse(new LispLastElement().canTip(createTestExpressionFromString("x = l.get(l.size()-1)")));
    }

    public void testCreateReplacement1() {
        PsiMethodCallExpression methodCallExpression = createTestMethodCallExpression("l.get", "l.size()-1");
        assertTrue(new LispLastElement().canTip(methodCallExpression));
        assertEquals("last(l)", new LispLastElement().createReplacement(methodCallExpression).getText());
    }

    public void testCreateReplacement2() {
        PsiMethodCallExpression methodCallExpression = createTestMethodCallExpression("x.get", "x.size()-1");
        assertTrue(new LispLastElement().canTip(methodCallExpression));
        assertEquals("last(x)", new LispLastElement().createReplacement(methodCallExpression).getText());
    }
}
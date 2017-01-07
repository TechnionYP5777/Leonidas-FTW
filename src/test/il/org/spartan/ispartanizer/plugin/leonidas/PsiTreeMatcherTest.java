package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiExpression;
import il.org.spartan.ispartanizer.tippers.TipperTest;

/**
 * Created by melanyc on 1/7/2017.
 */
public class PsiTreeMatcherTest extends TipperTest {

    public void testMatch1() {
        PsiExpression x = createTestExpressionFromString("x + 1");
        PsiExpression y = createTestExpressionFromString("x + 1");
        assertTrue(PsiTreeMatcher.match(x, y));
        PsiExpression z = createTestExpressionFromString("y + 1");
        assertTrue(PsiTreeMatcher.match(x, z));
    }

    public void testMatch2() {
        PsiCodeBlock b = createTestCodeBlockFromString("{ int x = 5; }");
        b.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, 1);
        PsiCodeBlock y = createTestCodeBlockFromString("{ int y = 10; }");
        assertTrue(PsiTreeMatcher.match(b, y));
    }

}
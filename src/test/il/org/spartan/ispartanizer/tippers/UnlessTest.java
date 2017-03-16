package il.org.spartan.ispartanizer.tippers;

import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiExpression;
import il.org.spartan.ispartanizer.PsiTypeHelper;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.tippers.Unless;
import org.junit.Test;

/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class UnlessTest extends PsiTypeHelper {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testcanTipThen() throws Exception {
        PsiConditionalExpression k = createTestConditionalExpression("x > 0", "null", "x");
        assert (iz.nullExpression(k.getThenExpression()));
        assert ((new Unless()).canTip(k));

    }

    @Test
    public void testcantTip() throws Exception {
        PsiConditionalExpression k = createTestConditionalExpression("x > 0", "_null", "x");
        assert (!iz.nullExpression(k.getThenExpression()));
        assert (!(new Unless()).canTip(k));
    }

    @Test
    public void test1() throws Exception {
        PsiConditionalExpression k = createTestConditionalExpression("x > 0", "x", "null");
        assert (!iz.nullExpression(k.getThenExpression()));
        assert (!(new Unless()).canTip(k));
    }

    @Test
    public void testCreateReplacement() throws Exception {
        PsiConditionalExpression k = createTestConditionalExpression("x > 0", "null", "x");
        assert (iz.nullExpression(k.getThenExpression()));
        Unless u = new Unless();
        assert (u.canTip(k));
        PsiExpression e = createTestExpression("eval(x).unless(x > 0)");
        assertEqualsByText(u.createReplacement(k), e);
    }
}
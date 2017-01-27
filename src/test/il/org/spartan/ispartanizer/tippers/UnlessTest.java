package il.org.spartan.ispartanizer.tippers;

import com.intellij.psi.PsiConditionalExpression;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.tippers.Unless;

/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class UnlessTest extends TipperTest {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testcanTipThen() throws Exception {
        PsiConditionalExpression k = createTestConditionalExpression("x > 0", "null", "x");
        assert (iz.nullExpression(k.getThenExpression()));
        assert ((new Unless()).canTip(k));

    }

    public void testcantTip() throws Exception {
        PsiConditionalExpression k = createTestConditionalExpression("x > 0", "_null", "x");
        assert (!iz.nullExpression(k.getThenExpression()));
        assert (!(new Unless()).canTip(k));
    }

    public void test1() throws Exception {
        PsiConditionalExpression k = createTestConditionalExpression("x > 0", "x", "null");
        assert (!iz.nullExpression(k.getThenExpression()));
        assert (!(new Unless()).canTip(k));
    }

    public void testCreateReplacement() throws Exception {
        PsiConditionalExpression k = createTestConditionalExpression("x > 0", "null", "x");
        assert (iz.nullExpression(k.getThenExpression()));
        Unless u = new Unless();
        assert (u.canTip(k));
        assertEqualsByText(u.createReplacement(k), createTestExpression("eval(x).unless(x > 0)"));
    }
}
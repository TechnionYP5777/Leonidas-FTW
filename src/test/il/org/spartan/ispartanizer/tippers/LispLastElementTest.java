package il.org.spartan.ispartanizer.tippers;

import il.org.spartan.ispartanizer.plugin.tippers.LispLastElement;
import il.org.spartan.ispartanizer.tippers.TipperTest;
import junit.framework.TestCase;

/**
 * @author AnnaBel7
 * @since 12/01/2017.
 */
public class LispLastElementTest extends TipperTest {
    public void testCanTip1() {
        assertEquals(true, new LispLastElement().canTip(createTestExpressionFromString("l.get(l.size()-1)")));
    }

    public void testCanTip2() {

    }

    public void testCanTip3() {

    }

    public void testCreateReplacement1() {

    }

    public void testCreateReplacement2() {

    }

    public void testCreateReplacement3() {

    }
}
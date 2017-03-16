package il.org.spartan.ispartanizer.plugin.leonidas;

import il.org.spartan.ispartanizer.PsiTypeHelper;


/**
 * @author AnnaBel7
 * @since 16/01/2017.
 */
public class AmountTest extends PsiTypeHelper {
    public void testAny() {
        Amount amount = Amount.ANY;
        assertTrue(amount.conforms(1));
        assertTrue(amount.conforms(10));

        assertFalse(amount.notConforms(5));
        assertFalse(amount.notConforms(32));
    }

    public void testEmpty() {
        Amount amount = Amount.EMPTY;
        assertTrue(amount.conforms(0));
        assertFalse(amount.conforms(1));
        assertFalse(amount.conforms(10));

        assertFalse(amount.notConforms(0));
        assertTrue(amount.notConforms(1));
        assertTrue(amount.notConforms(10));
    }

    public void testExactlyOne() {
        Amount amount = Amount.EXACTLY_ONE;
        assertTrue(amount.conforms(1));
        assertFalse(amount.conforms(0));
        assertFalse(amount.conforms(10));

        assertFalse(amount.notConforms(1));
        assertTrue(amount.notConforms(0));
        assertTrue(amount.notConforms(10));
    }

    public void testAtLeastOne() {
        Amount amount = Amount.AT_LEAST_ONE;
        assertTrue(amount.conforms(1));
        assertTrue(amount.conforms(50));
        assertFalse(amount.conforms(0));

        assertFalse(amount.notConforms(1));
        assertFalse(amount.notConforms(10));
        assertTrue(amount.notConforms(0));
    }

}
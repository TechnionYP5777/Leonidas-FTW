package il.org.spartan.ispartanizer.tippers;

import il.org.spartan.ispartanizer.plugin.tippers.DefaultsTo;


/**
 * @author Oren Afek
 * @since 2016.12.26
 */


public class DefaultToTest extends TipperTest {


    public void testCanTipFirstElementIsNotNullSecondIsNEOperator() {
        assertTrue(new DefaultsTo().canTip(
                createTestExpressionFromString("x != null ? x : y")));
    }

    public void testCanTipFirstElementIsNotNullSecondIsEQOperator() {
        assertTrue(new DefaultsTo().canTip(
                createTestExpressionFromString("x == null ? y : x")));
    }

    public void testCanTipFirstElementIsNullSecondIsNotEQOperator() {
        assertTrue(new DefaultsTo().canTip(
                createTestExpressionFromString("null == x ? y : x")));
    }

    public void testCanTipFirstElementIsNullSecondIsNotNEOperator() {
        assertTrue(new DefaultsTo().canTip(
                createTestExpressionFromString("null != x ? x : y")));
    }

    public void testCannotTipTwoElementsAreNullEQOperator() {
        assertFalse(new DefaultsTo().canTip(
                createTestExpressionFromString("null == null ? x : y")));
    }

    public void testCannotTipTwoElementsAreNullNEOperator() {
        assertFalse(new DefaultsTo().canTip(
                createTestExpressionFromString("null != null ? x : y")));
    }

    public void testCannotTipTwoElementsAreNotNullEQOperator() {
        assertFalse(new DefaultsTo().canTip(
                createTestExpressionFromString("x == y ? x : y")));
    }

    public void testCannotTipTwoElementsAreNotNullNEOperator() {
        assertFalse(new DefaultsTo().canTip(
                createTestExpressionFromString("x != y ? x : y")));
    }

    public void testCannotTipCondVarsAndThenElseVarsDontMatch() {
        assertFalse(new DefaultsTo().canTip(
                createTestExpressionFromString("x != null ? y : z")));
    }

    public void testCannotTipOperatorAndOrderMismatch1() {
        assertFalse(new DefaultsTo().canTip(
                createTestExpressionFromString("x != null ? y : x")));
    }

    public void testCannotTipOperatorAndOrderMismatch2() {
        assertFalse(new DefaultsTo().canTip(
                createTestExpressionFromString("x == null ? x : y")));
    }

    public void testCannotTipOperatorAndOrderMismatch3() {
        assertFalse(new DefaultsTo().canTip(
                createTestExpressionFromString("null == x ? x : y")));
    }

    public void testCannotTipOperatorAndOrderMismatch4() {
        assertFalse(new DefaultsTo().canTip(
                createTestExpressionFromString("null != x ? y : x")));
    }


}

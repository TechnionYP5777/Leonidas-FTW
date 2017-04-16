package il.org.spartan.leonidas.tippers;

import com.intellij.psi.PsiConditionalExpression;
import il.org.spartan.leonidas.PsiTypeHelper;
import il.org.spartan.leonidas.plugin.tippers.DefaultsTo;

import java.util.Arrays;


/**
 * @author Oren Afek
 * @since 2016.12.26
 */


public class DefaultToTest extends PsiTypeHelper {

    private final String legalCaseString1 = "x != null ? x : y";
    private final String legalCaseString2 = "x == null ? y : x";
    private final String legalCaseString3 = "null == x ? y : x";
    private final String legalCaseString4 = "null != x ? x : y";

    private final String[] legalCasesStrings = {legalCaseString1, legalCaseString2, legalCaseString3, legalCaseString4};
    private final String legalReplacement = "defaults(x).to(y)";

    public void testCanTipFirstElementIsNotNullSecondIsNEOperator() {
        assertTrue(new DefaultsTo().canTip(
                createTestExpressionFromString(legalCaseString1)));
    }

    public void testCanTipFirstElementIsNotNullSecondIsEQOperator() {
        assertTrue(new DefaultsTo().canTip(
                createTestExpressionFromString(legalCaseString2)));
    }

    public void testCanTipFirstElementIsNullSecondIsNotEQOperator() {
        assertTrue(new DefaultsTo().canTip(
                createTestExpressionFromString(legalCaseString3)));
    }

    public void testCanTipFirstElementIsNullSecondIsNotNEOperator() {
        assertTrue(new DefaultsTo().canTip(
                createTestExpressionFromString(legalCaseString4)));
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

    public void testCreateReplacementLegalCases() {
        Arrays.stream(legalCasesStrings).forEach(s ->
                assertEqualsByText(new DefaultsTo().createReplacement(
                        createConditionalExpressionFromLegalString(s)),
                        createTestExpression(legalReplacement)));
    }

    private PsiConditionalExpression createConditionalExpressionFromLegalString(String conditionalString) {
        int indexOfQuestionMark = conditionalString.indexOf('?');
        int indexOfColon = conditionalString.indexOf(':');
        String condition = conditionalString.substring(0, indexOfQuestionMark).trim();
        String then = conditionalString.substring(indexOfQuestionMark + 1, indexOfColon).trim();
        String else$ = conditionalString.substring(indexOfColon + 1).trim();
        return createTestConditionalExpression(condition, then, else$);
    }

}

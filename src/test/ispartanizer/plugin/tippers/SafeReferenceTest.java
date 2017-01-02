package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.PsiTestCase;

/**
 * Created by amirsagiv on 12/24/16.
 */
public class SafeReferenceTest extends PsiTestCase {

    public void testCanTipFirstScenarioLegal() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x == null ? null : x.y", e);
        SafeReference sf = new SafeReference();
        assertTrue(sf.canTip(exp));
    }

    public void testCanTipSecondScenarioLegal() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("null == x ? null : x.y", e);
        SafeReference sf = new SafeReference();
        assertTrue(sf.canTip(exp));
    }

    public void testCanTipThirdScenarioLegal() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x != null ? x.y : null", e);
        SafeReference sf = new SafeReference();
        assertTrue(sf.canTip(exp));
    }

    public void testCanTipFourthScenarioLegal() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("null != x ? x.y : null", e);
        SafeReference sf = new SafeReference();
        assertTrue(sf.canTip(exp));
    }

    public void testCanTipIllegal1() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x == null ? null : null", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal2() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x == null ? x.y : null", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal3() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x != null ? null : x.y", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal4() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x != null ? null : null", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal5() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("y == null ? null : x.y", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal6() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("y != null ? x.y: null", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal7() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x >= null ? x.y: null", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal8() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("null < x ? x.y: null", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal9() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x == null ? null: a.y", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal10() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("x != null ? a.y: null", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal11() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("null == x ? null: a.y", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }

    public void testCanTipIllegal12() {
        PsiFile f = createDummyFile("test.java", "");
        PsiElement e = f.getNode().getPsi();
        PsiExpression exp = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("null != x ? a.y: null", e);
        SafeReference sf = new SafeReference();
        assertFalse(sf.canTip(exp));
    }


}

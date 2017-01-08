package il.org.spartan.ispartanizer.tippers;

import com.intellij.psi.*;
import com.intellij.testFramework.PsiTestCase;

/**
 * @author Oren Afek
 * @since 26/12/16
 */
public abstract class TipperTest extends PsiTestCase {

    private static final String dummyTestFileName = "test.java";
    private static final String emptyText = "";

    private PsiElement getTestElement() {
        PsiFile f = createDummyFile(dummyTestFileName, emptyText);
        return f.getNode().getPsi();
    }

    private PsiElementFactory getTestFactory() {
        return JavaPsiFacade.getElementFactory(getTestElement().getProject());
    }

    protected PsiStatement createTestStatementFromString(String s) {
        return getTestFactory().createStatementFromText(s, getTestElement());
    }

    protected PsiExpression createTestExpressionFromString(String s) {
        return getTestFactory().createExpressionFromText(s, getTestElement());
    }

    protected PsiElement createTestClassFromString(String s) {
        PsiFile f = createDummyFile(dummyTestFileName, s);
        return f.getNode().getPsi();
    }

    protected PsiMethod createTestMethodFromString(String s) {
        return getTestFactory().createMethodFromText(s, getTestElement());
    }

    protected PsiCodeBlock createTestCodeBlockFromString(String s) {
        return getTestFactory().createCodeBlockFromText(s, getTestElement());
    }

    protected PsiFile createTestFileFromString(String s) {
        return createDummyFile(dummyTestFileName, s);
    }

    protected PsiLiteralExpression createTestNullExpression() {
        return (PsiLiteralExpression) getTestFactory().createExpressionFromText("null", getTestElement());
    }

    protected PsiType createTestType(String s) {
        return getTestFactory().createType(getTestFactory().createClass(s));
    }

    protected PsiDeclarationStatement createTestDeclarationStatement(String name, String type, String initializer) {
        PsiType t = createTestType(type);
        PsiExpression i = createTestExpressionFromString(initializer);
        return getTestFactory().createVariableDeclarationStatement(name, t, i);
    }

    protected PsiIfStatement createTestIfStatement(String cond, String then) {
        return (PsiIfStatement) getTestFactory()
                .createStatementFromText("if (" + cond + ") {" + then + "} ", getTestElement());
    }


}

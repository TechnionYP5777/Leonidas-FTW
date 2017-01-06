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

    PsiStatement createTestStatementFromString(String s) {
        return getTestFactory().createStatementFromText(s, getTestElement());
    }

    PsiExpression createTestExpressionFromString(String s) {
        return getTestFactory().createExpressionFromText(s, getTestElement());
    }

    PsiElement createTestClassFromString(String s) {
        PsiFile f = createDummyFile(dummyTestFileName, s);
        return f.getNode().getPsi();
    }

    PsiMethod createTestMethodFromString(String s){
        return getTestFactory().createMethodFromText(s, getTestElement());
    }

    PsiCodeBlock createTestCodeBlockFromString(String s){
        return getTestFactory().createCodeBlockFromText(s, getTestElement());
    }

}

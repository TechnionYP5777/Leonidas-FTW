package il.org.spartan.ispartanizer.tippers;

import com.intellij.psi.*;
import com.intellij.testFramework.PsiTestCase;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;
import il.org.spartan.ispartanizer.auxilary_layer.Wrapper;
import il.org.spartan.ispartanizer.plugin.tipping.Tipper;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Oren Afek
 * @since 26/12/16
 */
public abstract class TipperTest extends PsiTestCase {

    private static final String dummyTestFileName = "test.java";
    private static final String emptyText = "";

    private PsiFile getTestFile() {
        return createDummyFile(dummyTestFileName, emptyText);
    }

    private PsiElementFactory getTestFactory() {
        return JavaPsiFacade.getElementFactory(getTestFile().getProject());
    }

    protected PsiStatement createTestStatementFromString(String s) {
        return getTestFactory().createStatementFromText(s, getTestFile());
    }

    protected PsiExpression createTestExpressionFromString(String s) {
        return getTestFactory().createExpressionFromText(s, getTestFile());
    }

    protected PsiClass createTestClassFromString(String s) {
        return getTestFactory().createClassFromText(s, getTestFile());
    }

    /**
     * doesn't work if there are several classes inside each other.
     *
     * @param f
     * @return
     */
    protected PsiClass getClassInFile(PsiFile f) {
        Wrapper<PsiClass> classWrapper = new Wrapper<>(null);
        f.acceptChildren(new JavaElementVisitor() {
            @Override
            public void visitClass(PsiClass aClass) {
                classWrapper.set(aClass);
            }
        });
        return classWrapper.get();
    }


    protected PsiMethod createTestMethodFromString(String s) {
        return getTestFactory().createMethodFromText(s, getTestFile());
    }

    protected PsiEnumConstant createTestEnumFromString(String s) {
        return getTestFactory().createEnumConstantFromText(s, getTestFile());
    }

    protected PsiField createTestFieldDeclarationFromString(String s) {
        return getTestFactory().createFieldFromText(s, getTestFile());
    }

    protected PsiTypeElement createTestTypeElementFromString(String s) {
        return getTestFactory().createTypeElementFromText(s, getTestFile());
    }

    protected PsiIdentifier createTestIdentifierFromString(String s) {
        return getTestFactory().createIdentifier(s);
    }

    protected PsiForStatement createTestForStatementFromString(String s) {
        return (PsiForStatement) getTestFactory().createStatementFromText(s, getTestFile());
    }

    protected PsiForeachStatement createTestForeachStatementFromString(String s) {
        return (PsiForeachStatement) getTestFactory().createStatementFromText(s, getTestFile());
    }

    protected PsiImportList createTestImportListFromString(String s) {
        PsiFile file = createTestFileFromString(s +
                "public class A{}");
        PsiElement importList = file.getNavigationElement().getFirstChild();
        return (PsiImportList) importList;
    }

    protected PsiCodeBlock createTestCodeBlockFromString(String s) {
        return getTestFactory().createCodeBlockFromText(s, getTestFile());
    }

    protected PsiFile createTestFileFromString(String s) {
        return createDummyFile(dummyTestFileName, s);
    }

    protected PsiLiteralExpression createTestNullExpression() {
        return (PsiLiteralExpression) getTestFactory().createExpressionFromText("null", getTestFile());
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
                .createStatementFromText("if (" + cond + ") {" + then + "} ", getTestFile());
    }

    protected PsiConditionalExpression createTestConditionalExpression(String cond, String then, String else$) {
        return (PsiConditionalExpression) getTestFactory()
                .createExpressionFromText(cond + " ? " + then + " : " + else$, getTestFile());
    }

    protected void printPsi(PsiElement e) {
        Wrapper<Integer> tabs = new Wrapper<>(0);
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                IntStream.range(0, tabs.get()).forEach(x -> System.out.print("\t"));
                System.out.println(element);
                tabs.set(tabs.get() + 1);
                super.visitElement(element);
            }
        });
    }

    protected PsiMethodCallExpression createTestMethodCallExpression(String methodName, String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(methodName).append("(");
        Arrays.stream(args).forEach(f -> sb.append(f));
        sb.append(")");
        return (PsiMethodCallExpression) getTestFactory()
                .createExpressionFromText(sb.toString(), getTestFile());
    }

    protected PsiExpression createTestExpression(String expression) {
        return getTestFactory()
                .createExpressionFromText(expression, getTestFile());
    }

    protected boolean equalsByText(PsiElement e1, PsiElement e2) {
        return (e1 == null && e2 == null) || (e1 != null && e2 != null && e1.getText().equals(e2.getText()));
    }

    protected void assertEqualsByText(PsiElement e1, PsiElement e2) {
        if(e1 == null && e2 == null){
            return;
        }

        assertNotNull(e1);
        assertNotNull(e2);
        assertEquals(e1.getText(), e2.getText());
    }

    protected <T extends PsiElement> boolean testTip(Tipper<T> tipper, T element, PsiElement res) {
        PsiElement parent = element.getParent();
        int numberOfChild = 0;
        for (PsiElement child = element.getParent().getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.equals(element)) {
                break;
            }
            numberOfChild++;
        }
        tipper.tip(element).go(new PsiRewrite());
        PsiElement newChild = parent.getChildren()[numberOfChild];
        return newChild.getText().equals(res.getText());
    }

}

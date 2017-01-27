package il.org.spartan.ispartanizer.tippers;

import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.testFramework.PsiTestCase;
import il.org.spartan.ispartanizer.auxilary_layer.Utils;
import il.org.spartan.ispartanizer.auxilary_layer.Wrapper;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Oren Afek, Roey Maor
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

    protected PsiStatement createTestStatementFromString(String ¢) {
        return getTestFactory().createStatementFromText(¢, getTestFile());
    }

    protected PsiExpression createTestExpressionFromString(String ¢) {
        return getTestFactory().createExpressionFromText(¢, getTestFile());
    }

    protected PsiClass createTestClassFromString(String s){
        PsiClass dummyClass = getTestFactory().createClassFromText(s, getTestFile());
        return dummyClass == null ? null : Utils.getChildrenOfType(dummyClass, PsiClass.class).get(0);
    }

    protected PsiClass createTestClassFromString(String javadoc, String className, String classBody, String... classModifiers) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(classModifiers).forEach(m -> sb.append(m));
        sb.append("class");
        sb.append(className);
        sb.append("{");
        sb.append(classBody);
        sb.append("}");
        return getTestFactory().createClassFromText((sb + ""), getTestFile());
    }

    protected PsiClass createTestInterfaceFromString(String interfaceName, String interfaceBody, String... interfaceModifiers) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(interfaceModifiers).forEach(m -> sb.append(m));
        sb.append("interface");
        sb.append(interfaceName);
        sb.append("{");
        sb.append(interfaceBody);
        sb.append("}");
        return getTestFactory().createClassFromText((sb + ""), getTestFile());
    }

    protected PsiClass createEnumClassFromString(String ¢) {
        return getTestFactory().createEnum(¢);
    }

    protected PsiClass createInterfaceClassFromString(String ¢) {
        return getTestFactory().createInterface(¢);
    }

    protected PsiBlockStatement createTestBlockStatementFromString(String ¢) {
        return (PsiBlockStatement) getTestFactory().createStatementFromText(¢, getTestFile());
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

    protected PsiMethod createTestMethodFromString(String ¢) {
        return getTestFactory().createMethodFromText(¢, getTestFile());
    }

    protected PsiEnumConstant createTestEnumFromString(String ¢) {
        return getTestFactory().createEnumConstantFromText(¢, getTestFile());
    }

    protected PsiDeclarationStatement createTestEnumDecFromString(String ¢) {
        return (PsiDeclarationStatement) getTestFactory().createStatementFromText(¢, getTestFile());
    }

    protected PsiAnnotation createTestAnnotationFromString(String ¢) {
        return getTestFactory().createAnnotationFromText(¢, getTestFile());
    }

    protected PsiType createTestTypeFromString(String ¢) {
        return getTestFactory().createTypeElementFromText(¢, getTestFile()).getType();
    }

    protected PsiField createTestFieldDeclarationFromString(String ¢) {
        return getTestFactory().createFieldFromText(¢, getTestFile());
    }

    protected PsiTypeElement createTestTypeElementFromString(String ¢) {
        return getTestFactory().createTypeElementFromText(¢, getTestFile());
    }

    protected PsiIdentifier createTestIdentifierFromString(String ¢) {
        return getTestFactory().createIdentifier(¢);
    }

    protected PsiDocComment createTestDocCommentFromString(String ¢) {
        return getTestFactory().createDocCommentFromText(¢);
    }

    protected PsiComment createTestCommentFromString(String ¢) {
        return getTestFactory().createCommentFromText(¢, getTestFile());
    }

    protected PsiWhileStatement createTestWhileStatementFromString(String ¢) {
        return (PsiWhileStatement) getTestFactory().createStatementFromText(¢, getTestFile());
    }

    protected PsiDoWhileStatement createTestDoWhileStatementFromString(String ¢) {
        return (PsiDoWhileStatement) getTestFactory().createStatementFromText(¢, getTestFile());
    }

    protected PsiSwitchStatement createTestSwitchStatementFromString(String ¢) {
        return (PsiSwitchStatement) getTestFactory().createStatementFromText(¢, getTestFile());
    }

    protected PsiForStatement createTestForStatementFromString(String ¢) {
        return (PsiForStatement) getTestFactory().createStatementFromText(¢, getTestFile());
    }

    protected PsiForeachStatement createTestForeachStatementFromString(String ¢) {
        return (PsiForeachStatement) getTestFactory().createStatementFromText(¢, getTestFile());
    }

    protected PsiImportList createTestImportListFromString(String ¢) {
        return (PsiImportList) createTestFileFromString(¢ + "public class A{}").getNavigationElement().getFirstChild();
    }

    protected PsiCodeBlock createTestCodeBlockFromString(String ¢) {
        return getTestFactory().createCodeBlockFromText(¢, getTestFile());
    }

    protected PsiFile createTestFileFromString(String ¢) {
        return createDummyFile(dummyTestFileName, ¢);
    }

    protected PsiLiteralExpression createTestNullExpression() {
        return (PsiLiteralExpression) getTestFactory().createExpressionFromText("null", getTestFile());
    }

    protected PsiType createTestType(String ¢) {
        return getTestFactory().createType(getTestFactory().createClass(¢));
    }

    protected PsiDeclarationStatement createTestDeclarationStatement(String name, String type, String initializer) {
        return getTestFactory().createVariableDeclarationStatement(name, createTestType(type),
                createTestExpressionFromString(initializer));
    }

    protected PsiIfStatement createTestIfStatement(String cond, String then) {
        return (PsiIfStatement) getTestFactory()
                .createStatementFromText("if (" + cond + ") {" + then + "} ", getTestFile());
    }

    /**
     * @param cond
     * @param then
     * @return if then has only one statement returns PsiIfStatement, otherwise null
     */
    protected PsiIfStatement createTestIfStatementNoBraces(String cond, String then) {
        return StringUtils.countMatches(then, ";") != 1 ? null
                : (PsiIfStatement) getTestFactory().createStatementFromText("if (" + cond + ") " + then + " ",
                getTestFile());
    }

    protected PsiConditionalExpression createTestConditionalExpression(String cond, String then, String else$) {
        return (PsiConditionalExpression) getTestFactory()
                .createExpressionFromText(cond + " ? " + then + " : " + else$, getTestFile());
    }

    protected void printPsi(PsiElement e) {
        Wrapper<Integer> tabs = new Wrapper<>(0);
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement e) {
                IntStream.range(0, tabs.get()).forEach(x -> System.out.print("\t"));
                System.out.println(e);
                tabs.set(tabs.get() + 1);
                super.visitElement(e);
            }
        });
    }

    protected PsiMethodCallExpression createTestMethodCallExpression(String methodName, String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(methodName).append("(");
        Arrays.stream(args).forEach(f -> sb.append(f));
        sb.append(")");
        return (PsiMethodCallExpression) getTestFactory()
                .createExpressionFromText((sb + ""), getTestFile());
    }

    protected PsiExpression createTestExpression(String expression) {
        return getTestFactory()
                .createExpressionFromText(expression, getTestFile());
    }

    protected PsiTryStatement createTestTryStatement(String try$, String catch$, String do$) {
        return (PsiTryStatement) getTestFactory().createStatementFromText("try {" + try$ + "} catch(" + catch$ + "){" + do$ + "}", getTestFile());
    }

    protected PsiTryStatement createTestTryStatement(String try$, String catch$, String do$, String finally$) {
        return (PsiTryStatement) getTestFactory().createStatementFromText("try {" + try$ + "} catch(" + catch$ + "){" + do$ + "} finally {" + finally$ + "}", getTestFile());
    }

    protected boolean equalsByText(PsiElement e1, PsiElement e2) {
        return (e1 == null && e2 == null) || (e1 != null && e2 != null && e1.getText().equals(e2.getText()));
    }

    protected void assertEqualsByText(PsiElement e1, PsiElement e2) {
        if (e1 == null && e2 == null)
            return;

        assertNotNull(e1);
        assertNotNull(e2);
        assertEquals(removeWhiteSpaces(e1.getText()), removeWhiteSpaces(e2.getText()));
    }

    protected String removeWhiteSpaces(String ¢) {
        return ¢.replaceAll("\\s+", "");
    }

    protected PsiLambdaExpression createTestLambdaExpression(String ¢) {
        return (PsiLambdaExpression) getTestFactory().createExpressionFromText(¢, getTestFile());
    }

    protected PsiNewExpression createTestNewExpression(String typeName, String... parametes) {
        StringBuilder sb = new StringBuilder();
        sb.append("new ")
                .append(typeName)
                .append("(");
        Arrays.stream(parametes).forEach(s -> sb.append(s));
        sb.append(")");
        return (PsiNewExpression) getTestFactory().createExpressionFromText((sb + ""), getTestFile());
    }

    protected PsiMethodReferenceExpression createTestMethodReferenceEpression(String typeName, String methodName) {
        StringBuilder sb = new StringBuilder();
        sb.append(typeName)
                .append("::")
                .append(methodName);
        return (PsiMethodReferenceExpression) getTestFactory().createExpressionFromText((sb + ""), getTestFile());
    }

    protected PsiRequiresStatement createTestRequiresStatement(String module) {
        return (PsiRequiresStatement) getTestFactory().createStatementFromText("requires " + module + ";", getTestFile());
    }

    protected class Pair<T, K> {

        public T first;
        public K second;

        public Pair() {
            this(null, null);
        }

        public Pair(T first, K second) {
            this.first = first;
            this.second = second;
        }
    }
}

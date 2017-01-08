package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiEnumConstantImpl;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.PsiTypeElementImpl;
import com.intellij.psi.impl.source.tree.java.*;
import com.intellij.psi.tree.IElementType;
import il.org.spartan.ispartanizer.plugin.leonidas.LeonidasTipper;
import il.org.spartan.ispartanizer.plugin.leonidas.LeonidasTipper.StubName;

import java.util.Arrays;

import static com.intellij.psi.PsiModifier.PUBLIC;
import static com.intellij.psi.PsiModifier.STATIC;

/**
 * @author Oren Afek
 * @since 2016.12.1
 */

public enum iz {
    ;
    private static final String ABSTRACT = "abstract";

    private static boolean typeCheck(Class<? extends PsiElement> type, PsiElement element) {
        return element != null && type.isAssignableFrom(element.getClass());
    }

    public static boolean null$(PsiElement element) {
        return iz.literal(element) && az.literal(element).textMatches("null");
    }

    public static boolean notNull(PsiElement element) {
        return !null$(element);
    }

    public static boolean statement(PsiElement element) {
        return typeCheck(PsiStatement.class, element);
    }

    public static boolean block(PsiElement element) {
        return typeCheck(PsiCodeBlockImpl.class, element);
    }

    public static boolean blockStatement(PsiElement element) {
        return typeCheck(PsiBlockStatement.class, element);
    }

    public static boolean methodCallExpression(PsiElement element) {
        return typeCheck(PsiMethodCallExpression.class, element);
    }

    public static boolean declarationStatement(PsiElement element) {
        return typeCheck(PsiDeclarationStatement.class, element);
    }

    public static boolean enumConstant(PsiElement element) {
        return typeCheck(PsiEnumConstantImpl.class, element);
    }

    public static boolean fieldDeclaration(PsiElement element) {
        return typeCheck(PsiFieldImpl.class, element);
    }

    public static boolean abstract$(PsiMethod element) {
        return element.getModifierList().hasExplicitModifier(ABSTRACT);
    }

    public static boolean static$(PsiMethod element) {
        return element.getModifierList().hasExplicitModifier(STATIC);
    }

    public static boolean singleParameterMethod(PsiMethod element) {
        return element.getParameterList().getParameters().length == 1;
    }

    public static boolean method(PsiElement element) {
        return typeCheck(PsiMethod.class, element);
    }

    public static boolean void$(PsiMethod element) {
        return PsiType.VOID.equals(step.returnType(element));
    }

    public static boolean public$(PsiMethod element) {
        return element != null && element.getModifierList().hasExplicitModifier(PUBLIC);
    }

    public static boolean main(PsiMethod element) {
        return element != null &&
                public$(element) &&
                static$(element) &&
                "main".equals(element.getName()) &&
                iz.singleParameterMethod(element) &&
                "args".equals(step.name(step.firstParameter(element)));
    }

    public static boolean returnStatement(PsiElement element) {
        return typeCheck(PsiReturnStatementImpl.class, element);
    }

    public static boolean type(PsiElement element) {
        return typeCheck(PsiTypeElementImpl.class, element);
    }

    public static boolean methodInvocation(PsiElement element) {
        return typeCheck(PsiMethodCallExpressionImpl.class, element);
    }

    public static boolean expressionStatement(PsiElement element) {
        return typeCheck(PsiExpressionStatementImpl.class, element);
    }

    public static boolean identifier(PsiElement element) {
        return typeCheck(PsiIdentifier.class, element);
    }

    public static boolean conditionalExpression(PsiElement element) {
        return typeCheck(PsiConditionalExpressionImpl.class, element);
    }

    public static boolean nullExpression(PsiExpression e) {
        return e.getText().equals("null");
    }

    public static boolean binaryExpression(PsiElement element) {
        return typeCheck(PsiBinaryExpressionImpl.class, element);
    }

    public static boolean referenceExpression(PsiExpression element) {
        return typeCheck(PsiReferenceExpressionImpl.class, element);
    }

    public static boolean equalsOperator(IElementType operator) {
        return operator != null && operator.equals(JavaTokenType.EQEQ);
    }

    public static boolean notEqualsOperator(IElementType operator) {
        return operator != null && operator.equals(JavaTokenType.NE);
    }

    public static boolean literal(PsiElement element) {
        return typeCheck(PsiLiteralExpression.class, element);
    }

    public static boolean classDeclaration(PsiElement element) {
        return typeCheck(PsiClassImpl.class, element);
    }

    public static boolean forStatement(PsiElement element) {
        return typeCheck(PsiForStatement.class, element);
    }

    public static boolean forEachStatement(PsiElement element) {
        return typeCheck(PsiForeachStatement.class, element);
    }

    public static boolean ifStatement(PsiElement element) {
        return typeCheck(PsiIfStatement.class, element);
    }

    public static boolean importList(PsiElement element) {
        return typeCheck(PsiImportList.class, element);
    }

    public static boolean javaToken(PsiElement element) {
        return typeCheck(PsiJavaToken.class, element);
    }

    public static boolean MethodCallExpression(PsiExpression element) {
        return typeCheck(PsiMethodCallExpressionImpl.class, element);
    }

    /**
     * @return true if e1 is of e2's type or inherits from it
     */
    public static boolean theSameType(PsiElement e1, PsiElement e2) {
        return typeCheck(e2.getClass(), e1);
    }


    public static boolean whiteSpace(PsiElement e) {
        return typeCheck(PsiWhiteSpace.class, e);
    }

    public static boolean ofType(PsiElement e, Class<? extends PsiElement> type) {
        return typeCheck(type, e);
    }

    public static boolean stubMethodCall(PsiElement element) {
        return iz.methodCallExpression(element) &&
                Arrays.stream(StubName.values())
                        .map(StubName::stubName)
                        .anyMatch(sn ->
                                sn.equals(az.methodCallExpression(element).getMethodExpression().getText()));
    }
}

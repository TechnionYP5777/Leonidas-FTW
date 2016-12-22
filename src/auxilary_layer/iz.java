package auxilary_layer;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiEnumConstantImpl;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.PsiTypeElementImpl;
import com.intellij.psi.impl.source.tree.java.PsiCodeBlockImpl;
import com.intellij.psi.impl.source.tree.java.PsiExpressionStatementImpl;
import com.intellij.psi.impl.source.tree.java.PsiMethodCallExpressionImpl;
import com.intellij.psi.impl.source.tree.java.PsiReturnStatementImpl;

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
        return element != null && element.getClass() == type;
    }

    public static boolean statement(PsiElement element) {
        return typeCheck(PsiStatement.class, element);
    }

    public static boolean block(PsiElement element) {
        return typeCheck(PsiCodeBlockImpl.class, element);
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

    public static boolean singleParameteredMethod(PsiMethod element) {
        return step.firstParameter(element) != null;
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
                iz.singleParameteredMethod(element) &&
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
}

package auxilary_layer;

import com.intellij.psi.*;

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
        return typeCheck(PsiCodeBlock.class, element);
    }

    public static boolean methodCallExpression(PsiElement element) {
        return typeCheck(PsiMethodCallExpression.class, element);
    }

    public static boolean abstract$(PsiMethod element) {
        return element.getModifierList().hasExplicitModifier(ABSTRACT);
    }

    public static boolean returnStatement(PsiElement element) {
        return typeCheck(PsiReturnStatement.class, element);
    }
}

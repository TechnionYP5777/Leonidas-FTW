package auxilary_layer;

import com.intellij.psi.*;

/**
 * @author Oren Afek
 * @since 2016.12.1
 */

public enum az {
    ;

    public static PsiStatement statement(PsiElement element) {
        return element != null ? (PsiStatement) element : null;
    }

    public static PsiCodeBlock blockBody(PsiElement element) {
        return element != null && iz.block(element) ? (PsiCodeBlock) element : null;
    }

    public static PsiStatement statementBody(PsiElement element) {
        return element != null && iz.statement(element) ? (PsiStatement) element : null;
    }

    public static PsiCodeBlock block(PsiElement element) {
        return element != null && iz.block(element) ? (PsiCodeBlock) element : null;
    }

    public static PsiDeclarationStatement declarationStatement(PsiElement element) {
             return element != null && iz.declarationStatement(element) ? (PsiDeclarationStatement) element : null;
    }

    public static PsiEnumConstant enumConstant(PsiElement element) {
           return element != null && iz.enumConstant(element) ? (PsiEnumConstant)element : null;
    }

    public static PsiField fieldDeclaration(PsiElement element) {
        return element != null && iz.fieldDeclaration(element) ? (PsiField) element : null;
    }

    public static PsiExpressionStatement expressionStatement(PsiElement element) {
        return element != null && iz.expressionStatement(element) ? (PsiExpressionStatement) element : null;
    }

    public static PsiMethodCallExpression methodInvocation(PsiElement element) {
        return element != null && iz.methodInvocation(element) ? (PsiMethodCallExpression) element : null;
    }

    public static PsiIdentifier identifier(PsiElement element) {
        return element != null && iz.identifier(element) ? (PsiIdentifier) element : null;
    }

    public static PsiConditionalExpression conditionalExpression(PsiElement element) {
        return element != null && iz.conditionalExpression(element) ? (PsiConditionalExpression) element : null;
    }

}

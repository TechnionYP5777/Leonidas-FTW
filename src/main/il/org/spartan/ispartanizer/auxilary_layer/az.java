package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;

/**
 * Utils class that helps converting Psi element to a specific Psi type
 * @author Oren Afek
 * @since 01-12-16
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

    public static PsiBlockStatement blockStatement(PsiElement element) {
        return element != null && iz.blockStatement(element) ? (PsiBlockStatement) element : null;
    }

    public static PsiDeclarationStatement declarationStatement(PsiElement element) {
        return element != null && iz.declarationStatement(element) ? (PsiDeclarationStatement) element : null;
    }

    public static PsiEnumConstant enumConstant(PsiElement element) {
        return element != null && iz.enumConstant(element) ? (PsiEnumConstant) element : null;
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

    public static PsiBinaryExpression binaryExpression(PsiElement element) {
        return element != null && iz.binaryExpression(element) ? (PsiBinaryExpression) element : null;
    }

    public static PsiReferenceExpression referenceExpression(PsiExpression element) {
        return element != null && iz.referenceExpression(element) ? (PsiReferenceExpression) element : null;
    }

    public static PsiLiteralExpression literal(PsiElement element) {
        return element != null && iz.literal(element) ? (PsiLiteralExpression) element : null;
    }

    public static PsiClass classDeclaration(PsiElement element) {
        return element != null && iz.classDeclaration(element) ? (PsiClass) element : null;
    }

    public static PsiForeachStatement forEachStatement(PsiElement element) {
        return element != null && iz.forEachStatement(element) ? (PsiForeachStatement) element : null;
    }

    public static PsiIfStatement ifStatement(PsiElement element) {
        return element != null && iz.ifStatement(element) ? (PsiIfStatement) element : null;
    }

    public static PsiReturnStatement returnStatement(PsiElement element) {
        return element != null && iz.returnStatement(element) ? (PsiReturnStatement) element : null;
    }

    public static PsiImportList importList(PsiElement element) {
        return element != null && iz.importList(element) ? (PsiImportList) element : null;
    }

    public static PsiJavaToken javaToken(PsiElement element) {
        return element != null && iz.javaToken(element) ? (PsiJavaToken) element : null;
    }

    public static PsiMethodCallExpression methodCallExpression(PsiElement element) {
        return element != null && iz.methodCallExpression(element) ? (PsiMethodCallExpression) element : null;
    }

    public static <T extends PsiElement> Integer integer(T value){
        return Integer.valueOf(value.getText());
    }

    public static PsiMethod method(PsiElement element) {
        return element != null && iz.method(element) ? (PsiMethod) element : null;
    }
}

package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;

/**
 * Utils class that helps converting Psi element to a specific Psi type
 * @author Oren Afek
 * @since 01-12-16
 */
public enum az {
    ;

    public static PsiStatement statement(PsiElement ¢) {
        return ¢ == null || !iz.statement(¢) ? null : (PsiStatement) ¢;
    }

    public static PsiCodeBlock block(PsiElement ¢) {
        return ¢ == null || !iz.block(¢) ? null : (PsiCodeBlock) ¢;
    }

    public static PsiBlockStatement blockStatement(PsiElement ¢) {
        return ¢ == null || !iz.blockStatement(¢) ? null : (PsiBlockStatement) ¢;
    }

    public static PsiDeclarationStatement declarationStatement(PsiElement ¢) {
        return ¢ == null || !iz.declarationStatement(¢) ? null : (PsiDeclarationStatement) ¢;
    }

    public static PsiEnumConstant enumConstant(PsiElement ¢) {
        return ¢ == null || !iz.enumConstant(¢) ? null : (PsiEnumConstant) ¢;
    }

    public static PsiField fieldDeclaration(PsiElement ¢) {
        return ¢ == null || !iz.fieldDeclaration(¢) ? null : (PsiField) ¢;
    }

    public static PsiExpressionStatement expressionStatement(PsiElement ¢) {
        return ¢ == null || !iz.expressionStatement(¢) ? null : (PsiExpressionStatement) ¢;
    }

    public static PsiIdentifier identifier(PsiElement ¢) {
        return ¢ == null || !iz.identifier(¢) ? null : (PsiIdentifier) ¢;
    }

    public static PsiConditionalExpression conditionalExpression(PsiElement ¢) {
        return ¢ == null || !iz.conditionalExpression(¢) ? null : (PsiConditionalExpression) ¢;
    }

    public static PsiBinaryExpression binaryExpression(PsiElement ¢) {
        return ¢ == null || !iz.binaryExpression(¢) ? null : (PsiBinaryExpression) ¢;
    }

    public static PsiReferenceExpression referenceExpression(PsiExpression element) {
        return element == null || !iz.referenceExpression(element) ? null : (PsiReferenceExpression) element;
    }

    public static PsiLiteralExpression literal(PsiElement ¢) {
        return ¢ == null || !iz.literal(¢) ? null : (PsiLiteralExpression) ¢;
    }

    public static PsiClass classDeclaration(PsiElement ¢) {
        return ¢ == null || !iz.classDeclaration(¢) ? null : (PsiClass) ¢;
    }

    public static PsiForeachStatement forEachStatement(PsiElement ¢) {
        return ¢ == null || !iz.forEachStatement(¢) ? null : (PsiForeachStatement) ¢;
    }

    public static PsiIfStatement ifStatement(PsiElement ¢) {
        return ¢ == null || !iz.ifStatement(¢) ? null : (PsiIfStatement) ¢;
    }

    public static PsiReturnStatement returnStatement(PsiElement ¢) {
        return ¢ == null || !iz.returnStatement(¢) ? null : (PsiReturnStatement) ¢;
    }

    public static PsiImportList importList(PsiElement ¢) {
        return ¢ == null || !iz.importList(¢) ? null : (PsiImportList) ¢;
    }

    public static PsiJavaToken javaToken(PsiElement ¢) {
        return ¢ == null || !iz.javaToken(¢) ? null : (PsiJavaToken) ¢;
    }

    public static PsiMethodCallExpression methodCallExpression(PsiElement ¢) {
        return ¢ == null || !iz.methodCallExpression(¢) ? null : (PsiMethodCallExpression) ¢;
    }

    public static <T extends PsiElement> Integer integer(T value){
        return Integer.valueOf(value.getText());
    }

    public static PsiMethod method(PsiElement ¢) {
        return ¢ == null || !iz.method(¢) ? null : (PsiMethod) ¢;
    }
}
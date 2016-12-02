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

    public static PsiDeclarationStatement declarationStatement(PsiElement element) {
        return element != null && iz.declarationStatement(element) ? (PsiDeclarationStatement) element : null;
    }

    public static PsiEnumConstant enumConstant(PsiElement element) {
        return element != null && iz.enumConstant(element) ? (PsiEnumConstant)element : null;
    }

    public static PsiField fieldDeclaration(PsiElement element) {
        return element != null && iz.fieldDecleration(element) ? (PsiField) element : null;
    }

    public static PsiStatement statementBody(PsiElement element) {
        return element != null && iz.statement(element) ? (PsiStatement) element : null;
    }
}

package auxilary_layer;

import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiStatement;

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
}

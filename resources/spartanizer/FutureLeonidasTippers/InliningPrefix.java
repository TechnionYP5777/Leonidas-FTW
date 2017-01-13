package spartanizer.LeonidasTippers;

import com.intellij.psi.PsiArrayAccessExpression;
import com.intellij.psi.PsiAssignmentExpression;
import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;

/**
 * Replace arr[index]; index++ with arr[index++]
 *
 * @author Oren Afek
 * @since 10/01/17
 */
public class InliningPrefix extends GenericPsiElement {

    @Leonidas(value = PsiArrayAccessExpression.class)
    public void from(){
        arrayIdentifier(0)[identifier(1)];
        identifier(1)++;

    }

    @Leonidas(value = PsiArrayAccessExpression.class)
    public void to(){
        arrayIdentifier(0)[identifier(1)++];
    }
}

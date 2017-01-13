package spartanizer.LeonidasTippers;

import com.intellij.psi.PsiArrayAccessExpression;
import com.intellij.psi.PsiAssignmentExpression;
import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;

/**
 * Replace if(!(!(b))); with if(b)
 *
 * @author Oren Afek
 * @since 01-12-17
 */
public class IfDoubleNot extends GenericPsiElement {

    @Leonidas(PsiIfStatement.class)
    public void from(){
        if(!(!(booleanExpression(0))))
            statement(1);


    }

    @Leonidas(PsiIfStatement.class)
    public void to(){
        if(booleanExpression(0))
            statement(1);

    }
}

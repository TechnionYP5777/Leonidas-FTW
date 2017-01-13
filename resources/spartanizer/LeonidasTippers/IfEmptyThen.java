package spartanizer.LeonidasTippers;

import com.intellij.psi.PsiArrayAccessExpression;
import com.intellij.psi.PsiAssignmentExpression;
import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;

/**
 * Replace if(b); else{s;} with if(!b){s;}
 *
 * @author Oren Afek
 * @since 10/01/17
 */
public class IfEmptyThen extends GenericPsiElement {

    @Leonidas(PsiIfStatement.class)
    public void from(){
        if(booleanExpression(0))
            ;
        else{
            statement(1);
        }
    }

    @Leonidas(PsiIfStatement.class)
    public void to(){
        if(!(booleanExpression(0)))
            statement(1);
    }
}

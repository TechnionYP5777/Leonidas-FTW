package spartanizer.LeonidasTippers;

import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;

/**
 * @author Oren Afek
 * @since 06/01/17
 */
public class RemoveCurlyBracesFromIfStatement extends GenericPsiElement {

    @Leonidas(value = PsiIfStatement.class)
    public void from(){
        if (booleanExpression(0)){
            statement(0);
        }
    }

    @Leonidas(PsiIfStatement.class)
    public void to(){
        if (booleanExpression(0))
            statement(0);
    }
}

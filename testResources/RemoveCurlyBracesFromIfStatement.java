package spartanizer.LeonidasTippers;

import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElementStub;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;

/**
 * Remove redundent curly braces
 * @author Oren Afek
 * @since 06/01/17
 */
public class RemoveCurlyBracesFromIfStatement extends GenericPsiElementStub {

    @Leonidas(value = PsiIfStatement.class)
    public void from(){
        if (booleanExpression(0)) {
            statement(1);
        }
    }

    @Leonidas(PsiIfStatement.class)
    public void to(){
        if (booleanExpression(0))
            statement(1);
    }
}

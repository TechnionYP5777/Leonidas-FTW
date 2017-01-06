package spartanizer.LeonidasTippers;

import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;
import il.org.spartan.ispartanizer.plugin.leonidas.LeonidasTipper;

/**
 * @author Oren Afek
 * @since 06/01/17
 */

public class RemoveCurlyBracesFromIfStatement extends LeonidasTipper {

    @Leonidas(PsiIfStatement.class)
    public void from(){
        if (booleanExpression()){
            statement();
        }
    }

    @Leonidas(PsiIfStatement.class)
    public void to(){
        if (booleanExpression())
            statement();
    }
}

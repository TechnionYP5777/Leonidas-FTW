package spartanizer.LeonidasTippers;

import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;
import il.org.spartan.ispartanizer.plugin.leonidas.LeonidasTipper;

/**
 * @author Oren Afek
 * @since 06/01/17
 */
@Leonidas(PsiIfStatement.class)
public class RemoveCurlyBracesFromIfStatement extends LeonidasTipper {

    public void from(){
        if (booleanExpression()){
            statement();
        }
    }

    public void to(){
        if (booleanExpression())
            statement();
    }
}

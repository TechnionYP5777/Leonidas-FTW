package spartanizer.LeonidasTippers;

import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;

/**
 * @author Oren Afek
 * @since 10/01/17
 */
public class InliningPrefix extends GenericPsiElement {

    @Leonidas(value = PsiIfStatement.class)
    public void from(){
        array[index];
        index++;

    }

    @Leonidas(PsiIfStatement.class)
    public void to(){
        array[index++];
    }
}

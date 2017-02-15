package spartanizer.FutureLeonidasTippers;

import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiIfStatement;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElementStub;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;

/**
 * Replace if(false){s1}else{s2} with s2
 *
 * @author AnnaBel7
 * @since 20/01/17
 */
public class IfFalseElse extends GenericPsiElementStub {

    @Leonidas(PsiIfStatement.class)
    public void from() {
        if (false) {
            statement();
        } else {
            statement(1);
        }
    }

    @Leonidas(PsiExpressionStatement.class)
    public void to() {
        statement(1);
    }
}

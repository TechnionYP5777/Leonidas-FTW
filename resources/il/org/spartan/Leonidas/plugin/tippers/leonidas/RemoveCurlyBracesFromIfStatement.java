package il.org.spartan.Leonidas.plugin.tippers.leonidas;

import com.intellij.psi.PsiIfStatement;
import il.org.spartan.Leonidas.plugin.leonidas.Leonidas;

import static il.org.spartan.Leonidas.plugin.leonidas.GenericPsiElementStub.booleanExpression;
import static il.org.spartan.Leonidas.plugin.leonidas.GenericPsiElementStub.statement;

/**
 * @author :O
 */
public class RemoveCurlyBracesFromIfStatement implements LeonidasTipperDefinition {

    @Override
    public void constraints() {

    }

    @Override
    @Leonidas(PsiIfStatement.class)
    public Template matcher() {
        return new Template(() -> {
            if (booleanExpression(0)) {
                statement();
            }
        });
    }

    @Override
    public Template replacer() {
        return new Template(() -> {
            if (booleanExpression(0))
                statement(1);
        });
    }


}

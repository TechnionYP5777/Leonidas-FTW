package il.org.spartan.ispartanizer.plugin.tippers.leonidas;

import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;

import static il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElementStub.booleanExpression;
import static il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElementStub.statement;
import static il.org.spartan.ispartanizer.plugin.leonidas.The.the;

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

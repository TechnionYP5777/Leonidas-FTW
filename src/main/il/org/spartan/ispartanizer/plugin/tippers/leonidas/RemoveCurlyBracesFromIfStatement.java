package il.org.spartan.ispartanizer.plugin.tippers.leonidas;

import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;
import il.org.spartan.ispartanizer.plugin.leonidas.Matcher;
import il.org.spartan.ispartanizer.plugin.leonidas.Replacer;

import static il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElementStub.booleanExpression;
import static il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElementStub.statement;

/**
 * @author :O
 */
@SuppressWarnings("Convert2Lambda")
public class RemoveCurlyBracesFromIfStatement extends LeonidasTipperDefinition {
    /**
     * yes
     */
    public RemoveCurlyBracesFromIfStatement() {
        super(new Matcher() {
            @Override
            @Leonidas(PsiIfStatement.class)
            protected void template() {
                if (booleanExpression(0)) {
                    statement(1);
                }
            }
        }, new Replacer() {
            @Override
            @Leonidas(PsiIfStatement.class)
            public void template() {
                if (booleanExpression(0))
                    statement(1);
            }
        });

    }
}

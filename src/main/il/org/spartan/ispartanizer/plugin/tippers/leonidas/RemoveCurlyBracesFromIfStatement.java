package il.org.spartan.ispartanizer.plugin.tippers.leonidas;

import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiIfStatement;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;
import il.org.spartan.ispartanizer.plugin.leonidas.MatcherBuilder;
import il.org.spartan.ispartanizer.plugin.leonidas.Replacer;
import il.org.spartan.ispartanizer.plugin.leonidas.Template;

import static il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElementStub.booleanExpression;
import static il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElementStub.statement;

/**
 * @author :O
 */
@SuppressWarnings("Convert2Lambda")
public class RemoveCurlyBracesFromIfStatement implements LeonidasTipperDefinition {
    static {

    }

    @Override
    public MatcherBuilder matcher() {
        return new MatcherBuilder() {
            @Override
            @Leonidas(PsiIfStatement.class)
            protected void template() {
                if (booleanExpression(0)) {
                    statement(1);
                }
            }
        }.butNot(1, new Template() {
            @Override
            @Leonidas(PsiExpression.class)
            public void template() {
                if (!booleanExpression(2)) {

                }
            }
        })
                .butNot(2, () -> {
                    if (!booleanExpression(3)) {
                    }
                });
    }

    @Override
    public Replacer replacer() {
        return new Replacer() {
            @Override
            @Leonidas(PsiIfStatement.class)
            public void template() {
                if (booleanExpression(0))
                    statement(1);
            }
        };
    }


}

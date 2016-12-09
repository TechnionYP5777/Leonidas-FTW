package plugin.tippers;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiForeachStatement;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;


/**
 * @author Oren Afek
 * @since 2016.12.1
 */
public class EnhancedForRedundantContinue implements Tipper<PsiForeachStatement> {

    @Override
    public boolean canTip(PsiElement element) {
        return false;
    }

    @Override
    public String description(PsiForeachStatement psiForeachStatement) {
        return null;
    }

    @Override
    public Tip tip(PsiForeachStatement node) {
        return null;
    }

    @Override
    public Class<PsiForeachStatement> getPsiClass() {
        return PsiForeachStatement.class;
    }
}

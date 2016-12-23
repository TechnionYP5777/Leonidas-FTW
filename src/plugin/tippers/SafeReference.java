package plugin.tippers;

import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

/**
 * Created by amirsagiv on 12/23/16.
 */
public class SafeReference implements Tipper<PsiConditionalExpression> {
    @Override
    public boolean canTip(PsiElement e) {
        return false;
    }

    @Override
    public String description(PsiConditionalExpression psiConditionalExpression) {
        return null;
    }

    @Override
    public Tip tip(PsiConditionalExpression node) {
        return null;
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return null;
    }
}

package plugin.tippers;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethodCallExpression;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

/**
 * @author AnnaBel7
 * @since 23/12/2016.
 */

public class LispLastElement implements Tipper<PsiMethodCallExpression> {


    private boolean canTip(PsiMethodCallExpression e) {
        return false;
    }

    @Override
    public boolean canTip(PsiElement e) {
        return e instanceof PsiMethodCallExpression && canTip((PsiMethodCallExpression) e);
    }

    @Override
    public String description(PsiMethodCallExpression psiMethodCallExpression) {
        return null;
    }

    @Override
    public Tip tip(PsiMethodCallExpression node) {
        return null;
    }

    @Override
    public Class<PsiMethodCallExpression> getPsiClass() {
        return PsiMethodCallExpression.class;
    }
}

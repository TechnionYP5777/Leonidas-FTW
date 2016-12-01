package plugin.tippers;

import com.intellij.psi.PsiLambdaExpression;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

/**
 * @author Oren Afek
 * @since 2016.12.1
 */

public class LambdaExpressionRemoveRedundantCurlyBraces implements Tipper<PsiLambdaExpression> {

    @Override
    public boolean canTip(PsiLambdaExpression element) {
        return false;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public Tip tip(PsiLambdaExpression node) {
        return null;
    }
}

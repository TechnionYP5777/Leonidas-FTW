package plugin.tippers;

import com.intellij.psi.PsiBinaryExpression;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

/**
 * @author Michal Cohen
 * @since 2016.12.1
 */
public class InfixFactorNegatives implements Tipper<PsiBinaryExpression> {

    @Override
    public boolean canTip(PsiBinaryExpression element) {
        return false;
    }

    @Override
    public String description(PsiBinaryExpression _) {
        return null;
    }

    @Override
    public String description(PsiBinaryExpression p) {
        return null;
    }

    @Override
    public Tip tip(PsiBinaryExpression node) {
        return null;
    }
}

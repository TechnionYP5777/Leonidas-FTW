package plugin.tippers;

import com.intellij.psi.PsiForeachStatement;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;


/**
 * @author Oren Afek
 * @since 2016.12.1
 */
public class EnhancedForRedundantContinue implements Tipper<PsiForeachStatement> {
    @Override
    public boolean canTip(PsiForeachStatement element) {
        return false;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public Tip tip(PsiForeachStatement node) {
        return null;
    }
}

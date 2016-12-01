package plugin.tippers;

import com.intellij.psi.PsiMethod;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

/**
 * @author Michal Cohen
 * @since 2016.12.1
 */

public class MethodDeclarationRenameSingleParameterToCent implements Tipper<PsiMethod> {

    @Override
    public boolean canTip(PsiMethod element) {
        return false;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public Tip tip(PsiMethod node) {
        return null;
    }
}

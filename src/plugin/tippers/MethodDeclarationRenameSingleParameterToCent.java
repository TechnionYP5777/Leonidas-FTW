package plugin.tippers;

import auxilary_layer.haz;
import auxilary_layer.iz;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

import static auxilary_layer.Utils.in;

/**
 * @author Michal Cohen
 * @since 2016.12.1
 */

public class MethodDeclarationRenameSingleParameterToCent implements Tipper<PsiMethod> {

    @Override
    public boolean canTip(PsiMethod m) {
        return !m.isConstructor() && iz.abstract$(m) && m.getParameterList().getParametersCount() == 1 && in(m.getParameterList().getParameters()[0].getName(), "$", "¢", "_", "__") && !haz.centVariableDefenition(m);
    }

    @Override
    public String description(PsiMethod m) {
        return m.getName();
    }

    @Override
    public Tip tip(PsiMethod m) {
        if (m == null || !canTip(m)) {
            return null;
        }
        return factory -> m.getParameterList().getParameters()[0].getNameIdentifier().replace(factory.createIdentifier("¢"));

    }
}

package plugin.tippers;

import auxilary_layer.haz;
import auxilary_layer.iz;
import auxilary_layer.step;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

import static auxilary_layer.Utils.in;

/**
 * @author Michal Cohen
 * @since 2016.12.1
 */

public class MethodDeclarationRenameSingleParameterToCent implements Tipper<PsiMethod> {

    private boolean canTip(PsiMethod m) {
        return !m.isConstructor() && !iz.abstract$(m) && iz.singleParameteredMethod(m) &&
                !iz.main(m) &&
                !in(step.name(step.firstParameter(m)), "$", "¢", "_", "__") &&
                !haz.centVariableDefenition(m);
    }


    @Override
    public boolean canTip(PsiElement e) {
        return e instanceof PsiMethod && canTip((PsiMethod) e);
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


        return factory -> m.getParameterList().getParameters()[0].getNameIdentifier().
                replace(JavaPsiFacade.getElementFactory(m.getProject()).createIdentifier("¢"));

    }

    @Override
    public Class<PsiMethod> getPsiClass() {
        return PsiMethod.class;
    }


}

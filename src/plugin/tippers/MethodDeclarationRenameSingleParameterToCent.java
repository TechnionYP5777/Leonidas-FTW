package plugin.tippers;

import auxilary_layer.*;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
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

        return new Tip(description(m), m, this.getClass()) {
            @Override public void go(final PsiRewrite r){
                // Done like this because who knows if it is mutable?
                PsiIdentifier cent = JavaPsiFacade.getElementFactory(m.getProject()).createIdentifier("¢");
                PsiIdentifier i = JavaPsiFacade.getElementFactory(m.getProject()).createIdentifier(m.getParameterList().getParameters()[0].getNameIdentifier().getText());
                r.replace(m.getParameterList().getParameters()[0].getNameIdentifier(),
                        cent);
                Utils.getAllReferences(m.getBody(), i).stream().forEach(s -> r.replace(s, cent));
            }
        };

    }

    @Override
    public Class<PsiMethod> getPsiClass() {
        return PsiMethod.class;
    }


}

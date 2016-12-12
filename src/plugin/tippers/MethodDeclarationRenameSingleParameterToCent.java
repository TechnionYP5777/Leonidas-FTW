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

    private boolean canTip(PsiMethod ¢) {
        return !¢.isConstructor() && !iz.abstract$(¢) && iz.singleParameteredMethod(¢) &&
                !iz.main(¢) &&
                !in(step.name(step.firstParameter(¢)), "$", "¢", "_", "__") &&
                !haz.centVariableDefenition(¢);
    }


    @Override
    public boolean canTip(PsiElement ¢) {
        return ¢ instanceof PsiMethod && canTip((PsiMethod) ¢);
    }

    @Override
    public String description(PsiMethod ¢) {
        return ¢.getName();
    }

    @Override
    public Tip tip(PsiMethod m) {
        return m == null || !canTip(m) ? null : new Tip(description(m), m, this.getClass()) {
            @Override
            public void go(final PsiRewrite r) {
                PsiIdentifier cent = JavaPsiFacade.getElementFactory(m.getProject()).createIdentifier("¢");
                PsiIdentifier i = JavaPsiFacade.getElementFactory(m.getProject()).createIdentifier(m.getParameterList().getParameters()[0].getNameIdentifier().getText());
                r.replace(m.getParameterList().getParameters()[0].getNameIdentifier(), cent);
                Utils.getAllReferences(m.getBody(), i).stream().forEach(s -> r.replace(s, cent));
            }
        };
    }

    @Override
    public Class<PsiMethod> getPsiClass() {
        return PsiMethod.class;
    }


}
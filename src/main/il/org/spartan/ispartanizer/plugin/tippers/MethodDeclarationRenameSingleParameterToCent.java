package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import il.org.spartan.ispartanizer.auxilary_layer.*;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;
import il.org.spartan.ispartanizer.plugin.tipping.Tipper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static il.org.spartan.ispartanizer.auxilary_layer.Utils.in;

/**
 * @author Michal Cohen
 * @since 2016.12.1
 */

public class MethodDeclarationRenameSingleParameterToCent implements Tipper<PsiMethod> {


    private boolean canTip(PsiMethod ¢) {
        return !¢.isConstructor() && !iz.abstract$(¢) && iz.singleParameterMethod(¢) &&
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
                List<PsiIdentifier> references = Utils.getAllReferences(m.getBody(), i);
                Stream<PsiIdentifier> fields = references.stream().filter(q -> iz.javaToken(q.getPrevSibling().getPrevSibling()));
                references.removeAll(fields.collect(Collectors.toList()));
                references.stream().forEach(s -> r.replace(s, cent));
            }
        };
    }

    @Override
    public Class<PsiMethod> getPsiClass() {
        return PsiMethod.class;
    }


}
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
 * @since 01-12-2016
 */
public class MethodDeclarationRenameSingleParameterToCent implements Tipper<PsiMethod> {

    private boolean canTip(PsiMethod ¢) {
        return !¢.isConstructor() && !iz.abstract$(¢) && iz.singleParameterMethod(¢) &&
                !iz.main(¢) &&
                !in(step.name(step.firstParameter(¢)), "$", "¢", "_", "__") &&
                !haz.centVariableDefinition(¢);
    }

    @Override
    public boolean canTip(PsiElement ¢) {
        return iz.method(¢) && canTip(az.method(¢));
    }

    @Override
    public String description(PsiMethod ¢) {
        return "change parameter to ¢";
    }

    @Override
    public Tip tip(PsiMethod ¢) {
        return ¢ == null || !canTip(¢) ? null : new Tip(description(¢), ¢, this.getClass()) {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void go(final PsiRewrite r) {
                PsiIdentifier cent = JavaPsiFacade.getElementFactory(¢.getProject()).createIdentifier("¢");
                PsiIdentifier i = JavaPsiFacade.getElementFactory(¢.getProject()).createIdentifier(¢.getParameterList().getParameters()[0].getNameIdentifier().getText());
                r.replace(¢.getParameterList().getParameters()[0].getNameIdentifier(), cent);
                List<PsiIdentifier> references = Utils.getAllReferences(¢.getBody(), i);
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
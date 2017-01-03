package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

/**
 * @author RoeiRaz
 * @since 1/3/2017
 */
public class Delegator extends JavadocMarkerNanoPattern {
    @Override
    protected boolean prerequisites(PsiMethod ¢) {
        return false;
    }

    @Override
    protected PsiElement createReplacement(PsiMethod e) {
        return null;
    }

    @Override
    public Class<PsiMethod> getPsiClass() {
        return PsiMethod.class;
    }
}

package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;

/**
 * @author RoeiRaz
 * @since 1/3/2017
 * <p>
 * Copied and migrated to work in IntellliJ.
 * Original author is Ori Marcovitch.
 */
public abstract class JavadocMarkerNanoPattern extends NanoPatternTipper<PsiMethod> {
    @Override
    public boolean canTip(PsiElement ¢) {
        return false;
    }

    @Override
    protected Tip pattern(PsiMethod ¢) {
        return null;
    }

    @Override
    public String description(PsiMethod psiMethod) {
        return null;
    }

    protected abstract boolean prerequisites(PsiMethod ¢);

    public final String tag() {
        return "[[" + this.getClass().getSimpleName() + "]]";
    }
}

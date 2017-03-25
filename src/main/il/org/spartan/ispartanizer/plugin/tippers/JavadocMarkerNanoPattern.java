package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import il.org.spartan.ispartanizer.auxilary_layer.Wrapper;
import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.auxilary_layer.step;

/**
 * <p>
 * Copied and migrated to work in IntellliJ.
 * Original author is Ori Marcovitch.
 *
 * @author RoeiRaz
 * @since 03-01-2017
 *
 */
public abstract class JavadocMarkerNanoPattern extends NanoPatternTipper<PsiMethod> {
    @Override
    public boolean canTip(PsiElement psiElement) {
        return iz.method(psiElement) && !hasTag(az.method(psiElement)) && prerequisites(az.method(psiElement));
    }

    @Override
    final public PsiElement createReplacement(PsiMethod e) {
        String docOld = step.docCommentString(e);
        String docNew = docOld + tag();
        final Wrapper<String> methodText = new Wrapper<>("");
        e.acceptChildren(new JavaElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                if (!iz.javadoc(element)) {
                    methodText.set(methodText.get() + element.getText());
                }
                super.visitElement(element);
            }
        });
        String methodNewText = "/**" + docNew + "*/" + methodText;
        return JavaPsiFacade.getElementFactory(e.getProject()).createMethodFromText(methodNewText, e.getContext());
    }

    @Override
    public String description(PsiMethod psiMethod) {
        return "Add \"Delegator\" javadoc";
    }

    protected abstract boolean prerequisites(PsiMethod ¢);

    public final String tag() {
        return "[[" + this.getClass().getSimpleName() + "]]";
    }

    private boolean hasTag(PsiMethod psiMethod) {
        return step.docCommentString(psiMethod).contains(tag());
    }
}

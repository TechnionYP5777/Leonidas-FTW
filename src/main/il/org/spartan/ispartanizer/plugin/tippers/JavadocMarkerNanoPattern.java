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
 * @author RoeiRaz
 * @since 1/3/2017
 * <p>
 * Copied and migrated to work in IntellliJ.
 * Original author is Ori Marcovitch.
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
        PsiMethod methodNew = JavaPsiFacade.getElementFactory(e.getProject()).createMethodFromText(methodNewText, e.getContext());
        return methodNew;
    }

    @Override
    public String description(PsiMethod psiMethod) {
        return "";
    }

    protected abstract boolean prerequisites(PsiMethod ¢);

    public final String tag() {
        return "[[" + this.getClass().getSimpleName() + "]]";
    }

    private final boolean hasTag(PsiMethod psiMethod) {
        return step.docCommentString(psiMethod).contains(tag());
    }
}

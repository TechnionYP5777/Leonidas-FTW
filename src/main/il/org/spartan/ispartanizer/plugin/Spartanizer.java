package il.org.spartan.ispartanizer.plugin;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * @author Oren Afek
 * @since 09-12-2016
 */
enum Spartanizer {
    ;

    static boolean canTip(PsiElement element) {
        return Toolbox.getInstance().canTip(element);
    }

    static void spartanizeElement(PsiElement element) {
        if (element.getContainingFile().getName().equals("SpartanizerUtils.java")) {
            return;
        }
        Toolbox toolbox = Toolbox.getInstance();
        toolbox.executeAllTippers(element);
    }

    static void spartanizeFileOnePass(PsiFile psiFile) {
        if (psiFile.getName().equals("SpartanizerUtils.java")) {
            return;
        }
        Toolbox toolbox = Toolbox.getInstance();
        psiFile.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                toolbox.executeAllTippers(element);
            }
        });
    }
}

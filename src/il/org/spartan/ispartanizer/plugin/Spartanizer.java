package il.org.spartan.ispartanizer.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * @author Oren Afek
 * @since 2016.12.9
 */

enum Spartanizer {
    ;

    static boolean canTip(PsiElement element) {
        return Toolbox.getInstance().canTip(element);
    }

    static void spartanizeCode(PsiClass psiClass, PsiElement element, Project project, PsiFile psiFile) {
        if (psiFile.getName().equals("SpartanizerUtils.java")) {
            return;
        }
        Toolbox toolbox = Toolbox.getInstance();
        toolbox.executeAllTippers(element, project, psiFile);
    }
}

package il.org.spartan.ispartanizer.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * @author Oren Afek
 * @since 09-12-2016
 */
enum Spartanizer {
    ;

    static boolean canTip(PsiElement ¢) {
        return Toolbox.getInstance().canTip(¢);
    }

    static void spartanizeCode(PsiClass __, PsiElement e, Project p, PsiFile f) {
        if (!"SpartanizerUtils.java".equals(f.getName()))
            Toolbox.getInstance().executeAllTippers(e, p, f);
    }
}

package plugin;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * Created by szarecki on 09/12/16.
 */
public enum Spartanizer {
    ;

    static boolean canTip(PsiElement element) {
        return Toolbox.getInstance().canTip(element);
    }

    static void spartanizeCode(PsiClass psiClass, PsiElement element, Project project, PsiFile psiFile) {
        Toolbox toolbox = Toolbox.getInstance();
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                toolbox.executeAllTippers(element, project, psiFile);
            }
        }.execute();
    }
}

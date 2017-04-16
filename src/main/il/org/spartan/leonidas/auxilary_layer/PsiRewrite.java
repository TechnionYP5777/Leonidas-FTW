package il.org.spartan.leonidas.auxilary_layer;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;

/**
 * This class helps performing replacement actions on Psi elements without having to create
 *  each time WriteCommandAction.
 * @author Roey Maor
 * @since 03-12-2016
 */
public class PsiRewrite {
    private PsiFileFactory fileFactory;
    private PsiFile psiFile;
    private Project project;

    public PsiRewrite psiFile(PsiFile ¢) {
        this.psiFile = ¢;
        return this;
    }

    public PsiRewrite project(Project ¢) {
        this.project = ¢;
        return this;
    }

    public PsiRewrite fileFactory(PsiFileFactory ¢) {
        this.fileFactory = ¢;
        return this;
    }

    public void replace(PsiElement element1, PsiElement element2) {
        new WriteCommandAction.Simple(project, psiFile) {
            @Override
            protected void run() throws Throwable {
                element1.replace(element2);
            }

        }.execute();
    }

    public PsiFileFactory getFileFactory() {
        return fileFactory;
    }
}

package auxilary_layer;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;

/**
 * @Author Roey Maor
 * @since 3/12/2016.
 */

public class PsiRewrite {
    private PsiFileFactory fileFactory;
    private PsiFile psiFile;
    private Project project;

    public PsiRewrite psiFile(PsiFile psiFile) {
        this.psiFile = psiFile;
        return this;
    }

    public PsiRewrite project(Project project) {
        this.project = project;
        return this;
    }

    public PsiRewrite fileFactory(PsiFileFactory fileFactory) {
        this.fileFactory = fileFactory;
        return this;
    }

    public void replace(PsiElement element1, PsiElement element2){
        new WriteCommandAction.Simple(project, psiFile) {
            @Override
            protected void run() throws Throwable {
                element1.replace(element2);
            }

        }.execute();
    }


    public PsiFileFactory getFileFactory(){
                return fileFactory;
    }
}

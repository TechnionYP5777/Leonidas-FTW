package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author Oren Afek
 * @since 06/01/17
 */
public interface PsiTreeTipperBuilder {

    PsiTreeTipperBuilder buildTipperPsiTree(String fileName, Project project) throws IOException;

    PsiElement getFromPsiTree();

    PsiElement getToPsiTree();

}

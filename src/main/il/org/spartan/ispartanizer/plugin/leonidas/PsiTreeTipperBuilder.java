package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import java.util.Map;

/**
 * @author Oren Afek
 * @since 06/01/17
 */
public interface PsiTreeTipperBuilder {

    PsiTreeTipperBuilder buildTipperPsiTree(String fileName, Project project);

    PsiElement getPsiTree();

    Map<PsiElement, ElementDescriptor> getTreeDescriptor();

}

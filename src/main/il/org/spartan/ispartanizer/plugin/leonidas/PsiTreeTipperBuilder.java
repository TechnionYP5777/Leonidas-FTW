package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import java.io.IOException;

/**
 * Builds a generic Psi tree from a Leonidas file.
 * @author Oren Afek
 * @since 06-01-17
 */
public interface PsiTreeTipperBuilder {

    /**
     * you must first build the tree before extracting the data from it.
     *
     * @param fileName - the file name of the Leonidas tipper to build.
     * @param project  - the current project.
     * @return - this, for fluency.
     * @throws IOException - if reading the file had failed.
     */
    PsiTreeTipperBuilder buildTipperPsiTree(String fileName, Project project) throws IOException;

    /**
     * @return - the generic Psi tree representing the pattern with which we match the users' code.
     */
    PsiElement getFromPsiTree();

    /**
     * @return - the generic Psi tree representing the pattern to which  the users' code is replaced.
     */
    PsiElement getToPsiTree();

}

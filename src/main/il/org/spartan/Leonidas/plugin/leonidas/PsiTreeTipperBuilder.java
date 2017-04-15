package il.org.spartan.Leonidas.plugin.leonidas;

import com.intellij.psi.PsiElement;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;

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
     * @return - this, for fluency.
     * @throws IOException - if reading the file had failed.
     */
    PsiTreeTipperBuilder buildTipperPsiTree(String fileName) throws IOException;

    /**
     * @return - the generic Psi tree representing the pattern with which we match the users' code.
     */
    EncapsulatingNode getFromPsiTree();

    /**
     * @return - the generic Psi tree representing the pattern to which  the users' code is replaced.
     */
    EncapsulatingNode getToPsiTree();

    Class<? extends PsiElement> getRootElementType();

    String getDescription();

}

package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;

import java.util.Map;

/**
 * This class provides replacing services for a given tree template.
 *
 * @author AnnaBel7 michalcohen
 * @since 09-01-2017
 */
public class Replacer {

    /**
     * This method replaces the given element by the corresponding tree built by PsiTreeTipperBuilder
     *
     * @param treeToReplace - the given tree that matched the "from" tree.
     * @param builder       - PsiTreeTipperBuilder that includes the "from" tree that matched treeToReplace
     * @param r             - Rewrite object
     * @return the replaced element
     */
    public static PsiElement replace(PsiElement treeToReplace, PsiTreeTipperBuilder builder, PsiRewrite r) {
        PsiElement templateMatchingTree = builder.getFromPsiTree();
        Map<Integer, PsiElement> map = PsiTreeMatcher.extractInfo(templateMatchingTree, treeToReplace);
        PsiElement templateReplacingTree = builder.getToPsiTree();
        // might not work due to replacing while recursively iterating.
        templateReplacingTree.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                if (element.getUserData(KeyDescriptionParameters.ORDER) != null) {
                    r.replace(element, map.get(element.getUserData(KeyDescriptionParameters.ORDER)));
                }
            }
        });
        r.replace(treeToReplace, templateReplacingTree);
        return templateReplacingTree;
    }
}

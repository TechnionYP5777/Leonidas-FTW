package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;
import il.org.spartan.ispartanizer.auxilary_layer.step;

import java.util.HashMap;
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
     * @param b       - PsiTreeTipperBuilder that includes the "from" tree that matched treeToReplace
     * @param r             - Rewrite object
     * @return the replaced element
     */
    public static PsiElement replace(PsiElement treeToReplace, PsiTreeTipperBuilder b, PsiRewrite r) {
        PsiElement $ = b.getToPsiTree();
        r.replace(treeToReplace, getReplacer(treeToReplace, b, r));
        return $;
    }

    public static PsiElement getReplacer(PsiElement treeToReplace, PsiTreeTipperBuilder b, PsiRewrite r) {
        PsiElement templateMatchingTree = b.getFromPsiTree();
        Map<Integer, PsiElement> map = extractInfo(templateMatchingTree, treeToReplace);
        PsiElement $ = b.getToPsiTree();
        // might not work due to replacing while recursively iterating.
        map.keySet().forEach(d -> {
            $.accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitElement(PsiElement ¢) {
                    super.visitElement(¢);
                    if (¢.getUserData(KeyDescriptionParameters.ID) != null)
                        r.replace(¢, map.get(¢.getUserData(KeyDescriptionParameters.ID)));
                }
            });
        });
        return $;
    }

    /**
     * @param treeTemplate - The root of a tree already been matched.
     * @param treeToMatch  - The patterns from which we extract the IDs
     * @return a mapping between an ID to a PsiElement
     */
    public static Map<Integer, PsiElement> extractInfo(PsiElement treeTemplate, PsiElement treeToMatch) {
        Map<Integer, PsiElement> $ = new HashMap<>();
        for (PsiElement treeTemplateChild = treeTemplate.getFirstChild(), treeToMatchChild = treeToMatch.getFirstChild(); treeTemplateChild != null && treeToMatchChild != null; treeTemplateChild = step.nextSibling(treeTemplateChild), treeToMatchChild = step.nextSibling(treeToMatchChild)) {
            if (treeTemplateChild.getUserData(KeyDescriptionParameters.ID) != null)
                $.put(treeTemplateChild.getUserData(KeyDescriptionParameters.ID), treeToMatchChild);
            $.putAll(extractInfo(treeTemplateChild, treeToMatchChild));
        }
        return $;
    }
}

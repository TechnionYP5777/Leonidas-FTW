package il.org.spartan.leonidas.plugin.leonidas;

import com.intellij.psi.PsiElement;
import il.org.spartan.leonidas.auxilary_layer.PsiRewrite;
import il.org.spartan.leonidas.auxilary_layer.step;
import il.org.spartan.leonidas.plugin.EncapsulatingNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oren Afek
 * @since 3/29/2017.
 */
public interface Replacer {

    /**
     * This method replaces the given element by the corresponding tree built by PsiTreeTipperBuilder
     *
     * @param treeToReplace - the given tree that matched the "from" tree.
     * @param r             - Rewrite object
     * @return the replaced element
     */
    default EncapsulatingNode replace(PsiElement treeToReplace, EncapsulatingNode templateMatchingTree, EncapsulatingNode templateReplacingTree, PsiRewrite r) {
        //EncapsulatingNode templateReplacingTree = builder.getToPsiTree();
        PsiElement n = getReplacer(treeToReplace, templateMatchingTree, templateReplacingTree, r);
        r.replace(treeToReplace, n);
        return EncapsulatingNode.buildTreeFromPsi(n);
    }

    default PsiElement getReplacer(PsiElement treeToReplace, EncapsulatingNode templateMatchingTree, EncapsulatingNode templateReplacingTree, PsiRewrite r) {
        Map<Integer, PsiElement> map = extractInfo(templateMatchingTree, treeToReplace);
        map.keySet().forEach(d -> templateReplacingTree.accept(e -> {
            if (e.getInner().getUserData(KeyDescriptionParameters.ID) != null && Pruning.getStubName(e).isPresent()) {
                Pruning.getRealParent(e, Pruning.getStubName(e).get()).replace(new EncapsulatingNode(map.get(e.getInner().getUserData(KeyDescriptionParameters.ID))));
            }
        }));

        return templateReplacingTree.getInner();
    }

    /**
     * @param treeTemplate - The root of a tree already been matched.
     * @param treeToMatch  - The patterns from which we extract the IDs
     * @return a mapping between an ID to a PsiElement
     */
    default Map<Integer, PsiElement> extractInfo(EncapsulatingNode treeTemplate, PsiElement treeToMatch) {
        Map<Integer, PsiElement> mapping = new HashMap<>();
        EncapsulatingNode.Iterator treeTemplateChile = treeTemplate.iterator();
        for (PsiElement treeToMatchChild = treeToMatch.getFirstChild(); treeTemplateChile.hasNext() && treeToMatchChild != null; treeTemplateChile.next(), treeToMatchChild = step.nextSibling(treeToMatchChild)) {
            if (treeTemplateChile.value().getInner().getUserData(KeyDescriptionParameters.ID) != null) {
                mapping.put(treeTemplateChile.value().getInner().getUserData(KeyDescriptionParameters.ID), treeToMatchChild);
            } else {
                mapping.putAll(extractInfo(treeTemplateChile.value(), treeToMatchChild));
            }
        }
        return mapping;
    }
}


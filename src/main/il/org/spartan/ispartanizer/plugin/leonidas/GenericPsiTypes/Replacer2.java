package il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes;

import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;
import il.org.spartan.ispartanizer.plugin.EncapsulatingNode;
import il.org.spartan.ispartanizer.plugin.leonidas.KeyDescriptionParameters;
import il.org.spartan.ispartanizer.plugin.leonidas.Matcher2;
import il.org.spartan.ispartanizer.plugin.leonidas.Pruning;

import java.util.Map;

/**
 * Created by  on 4/1/2017.
 */
public class Replacer2 {

    Matcher2 matcher;
    EncapsulatingNode root;

    public Replacer2(Matcher2 m, EncapsulatingNode r) {
        matcher = m;
        root = r;
    }

    /**
     * This method replaces the given element by the corresponding tree built by PsiTreeTipperBuilder
     *
     * @param treeToReplace - the given tree that matched the "from" tree.
     * @param r             - Rewrite object
     * @return the replaced element
     */
    public EncapsulatingNode replace(PsiElement treeToReplace, Map<Integer, PsiElement> map, PsiRewrite r) {
        PsiElement n = getReplacingTree(map, r);
        r.replace(treeToReplace, n);
        return EncapsulatingNode.buildTreeFromPsi(n);
    }

    public PsiElement getReplacingTree(Map<Integer, PsiElement> map, PsiRewrite r) {
        map.keySet().forEach(d -> root.accept(e -> {
            if (e.getInner().getUserData(KeyDescriptionParameters.ID) != null && Pruning.getStubName(e).isPresent()) {
                Pruning.getRealParent(e, Pruning.getStubName(e).get()).replace(new EncapsulatingNode(map.get(e.getInner().getUserData(KeyDescriptionParameters.ID))));
            }
        }));
        return root.getInner();
    }
}

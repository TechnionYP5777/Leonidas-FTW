package il.org.spartan.Leonidas.plugin.leonidas;

import com.intellij.psi.PsiElement;
import il.org.spartan.Leonidas.auxilary_layer.PsiRewrite;
import il.org.spartan.Leonidas.auxilary_layer.iz;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;

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
    public EncapsulatingNode replace(PsiElement treeToReplace, Map<Integer, PsiElement> m, PsiRewrite r) {
        PsiElement n = getReplacingTree(m, r);
        r.replace(treeToReplace, n);
        return EncapsulatingNode.buildTreeFromPsi(n);
    }

    public PsiElement getReplacingTree(Map<Integer, PsiElement> m, PsiRewrite r) {
        m.keySet().forEach(d -> root.accept(e -> {
            if (e.getInner().getUserData(KeyDescriptionParameters.ID) != null && iz.generic(e.getInner()))
                e.replace(new EncapsulatingNode(m.get(e.getInner().getUserData(KeyDescriptionParameters.ID))), r);
        }));
        return root.getInner();
    }
}

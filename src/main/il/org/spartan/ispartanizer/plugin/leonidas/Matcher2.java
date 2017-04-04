package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.auxilary_layer.step;
import il.org.spartan.ispartanizer.plugin.EncapsulatingNode;
import il.org.spartan.ispartanizer.plugin.EncapsulatingNodeValueVisitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by  on 3/31/2017.
 */
public class Matcher2 {

    private EncapsulatingNode root;
    private Map<Integer, List<Constraint>> constrains = new HashMap<>();

    public Matcher2() {
        root = null;
    }

    public Matcher2(EncapsulatingNode r) {
        root = r;
    }

    public Map<Integer, List<Constraint>> getMap() {
        return constrains;
    }

    public EncapsulatingNode getRoot() {
        return root;
    }

    public void setRoot(EncapsulatingNode e) {
        root = e;
    }

    public void addConstraint(Integer id, Constraint c) {
        constrains.putIfAbsent(id, new LinkedList<>());
        constrains.get(id).add(c);
    }

    public boolean match(PsiElement e) {
        if (!PsiTreeMatcher.match(root, EncapsulatingNode.buildTreeFromPsi(e))) {
            return false;
        }
        return extractInfo(root, e).keySet().stream().map(constrains::get)
                .map(
                        l -> l.stream()
                                .map(c -> c.match(e))
                                .reduce(true, (b1, b2) -> b1 && b2)
                )
                .reduce(true, (b1, b2) -> b1 && b2);
    }

    /**
     * @param treeTemplate - The root of a tree already been matched.
     * @param treeToMatch  - The patterns from which we extract the IDs
     * @return a mapping between an ID to a PsiElement
     */
    public Map<Integer, PsiElement> extractInfo(EncapsulatingNode treeTemplate, PsiElement treeToMatch) {
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

    /**
     * @param treeToMatch - The patterns from which we extract the IDs
     * @return a mapping between an ID to a PsiElement
     */
    public Map<Integer, PsiElement> extractInfo(PsiElement treeToMatch) {
        return extractInfo(root, treeToMatch);
    }


    public List<Integer> getGenericElements() {
        List<Integer> s = root.accept(e -> {
            if (iz.generic(e.getInner())) {
                List<Integer> tmp = new LinkedList<>();
                tmp.add(e.getInner().getUserData(KeyDescriptionParameters.ID));
                return tmp;
            }
            List<Integer> tmp = new LinkedList<>();
            tmp.add(-2);
            return tmp;
        }, (l1, l2) -> Stream.concat(l1.stream(), l2.stream()).collect(Collectors.toList()));
        return s.stream().filter(x -> x != -2).collect(Collectors.toList());
    }


}

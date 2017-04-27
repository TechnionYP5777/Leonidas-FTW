package il.org.spartan.Leonidas.plugin.leonidas;

import com.intellij.psi.PsiElement;
import il.org.spartan.Leonidas.auxilary_layer.iz;
import il.org.spartan.Leonidas.auxilary_layer.step;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;

import java.util.*;
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

    public void setRoot(EncapsulatingNode n) {
        root = n;
    }

    public void addConstraint(Integer id, Constraint c) {
        constrains.putIfAbsent(id, new LinkedList<>());
        constrains.get(id).add(c);
    }

    public List<Matcher2> getConstraintsMatchers() {
        return constrains.values().stream().map(List::stream).reduce(Stream::concat).get().map(c -> c.getMatcher()).collect(Collectors.toList());
    }

    public boolean match(PsiElement e) {
		return PsiTreeMatcher.match(root, EncapsulatingNode.buildTreeFromPsi(e)) && extractInfo(root, e).keySet().stream()
				.map(constrains::get).map(l -> l.stream().map(c -> c.match(e)).reduce(true, (b1, b2) -> b1 && b2))
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
        for (PsiElement treeToMatchChild = treeToMatch.getFirstChild(); treeTemplateChile.hasNext() && treeToMatchChild != null; treeTemplateChile.next(), treeToMatchChild = step.nextSibling(treeToMatchChild))
			if (treeTemplateChile.value().getInner().getUserData(KeyDescriptionParameters.ID) == null)
				mapping.putAll(extractInfo(treeTemplateChile.value(), treeToMatchChild));
			else
				mapping.put(treeTemplateChile.value().getInner().getUserData(KeyDescriptionParameters.ID),
						treeToMatchChild);
        return mapping;
    }

    /**
     * @param treeToMatch - The patterns from which we extract the IDs
     * @return a mapping between an ID to a PsiElement
     */
    public Map<Integer, PsiElement> extractInfo(PsiElement treeToMatch) {
        return extractInfo(root, treeToMatch);
    }


    /**
     * @return list of Ids of all the generic elements in the tipper.
     */
    public Set<Integer> getGenericElements() {
        final Set<Integer> tmp = new HashSet<>();
        root.accept(e -> {
            if (iz.generic(e.getInner())) {
                tmp.add(e.getInner().getUserData(KeyDescriptionParameters.ID));
            }
        });
        return tmp;
    }


}


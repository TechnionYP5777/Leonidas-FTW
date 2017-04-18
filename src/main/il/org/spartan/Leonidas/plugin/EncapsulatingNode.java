package il.org.spartan.Leonidas.plugin;


import com.intellij.psi.PsiElement;
import il.org.spartan.Leonidas.auxilary_layer.iz;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author michalcohen
 * @since 22-02-2017
 */
public class EncapsulatingNode implements Cloneable, Iterable<EncapsulatingNode> {
    private PsiElement inner;
    private EncapsulatingNode parent;
    private List<EncapsulatingNode> children = new LinkedList<>();

    public EncapsulatingNode(PsiElement e) {
        inner = e;
        Arrays.stream(e.getChildren()).forEach(child -> children.add(new EncapsulatingNode(child, this)));
    }

    private EncapsulatingNode(PsiElement e, EncapsulatingNode parent) {
        this(e);
        this.parent = parent;
    }

    public static EncapsulatingNode buildTreeFromPsi(PsiElement e) {
        return new EncapsulatingNode(e);
    }

    public EncapsulatingNode replace(EncapsulatingNode newNode) {
        if (parent == null)
			return this;
		if (!iz.generic(newNode.inner)) {
			inner.replace(newNode.inner);
			inner = newNode.inner;
		}
		parent.children.replaceAll(e -> e != this ? e : newNode);
		return this;
    }

    public List<EncapsulatingNode> getChildren() {
        return children;
    }

    public EncapsulatingNode getParent() {
        return parent;
    }

    public void accept(EncapsulatingNodeVisitor v) {
        children.forEach(child -> child.accept(v));
        v.visit(this);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public <T> T accept(EncapsulatingNodeValueVisitor v, BinaryOperator<T> accumulator) {
        return children.stream().map(child -> child.accept(v, accumulator)).reduce(accumulator).orElse(null);
    }

    public PsiElement getInner() {
        return inner;
    }

    public int getAmountOfNoneWhiteSpaceChildren() {
        return children.stream().filter(child -> !iz.whiteSpace(child.getInner())).collect(Collectors.toList()).size();
    }

    public String toString() {
        return inner.toString();
    }

    public String getText() {
        return inner.getText();
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public EncapsulatingNode clone() {
        return buildTreeFromPsi(inner);
    }

    @Override
    public EncapsulatingNode.Iterator iterator() {
        return new EncapsulatingNode.Iterator();
    }

    @Override
    public void forEach(Consumer<? super EncapsulatingNode> action) {
        children.stream().forEach(action);
    }

    public class Iterator implements java.util.Iterator<EncapsulatingNode> {
        int location;
        List<EncapsulatingNode> noSpaceChildren;

        public Iterator() {
            noSpaceChildren = children.stream().filter(child -> !iz.whiteSpace(child.getInner())).collect(Collectors.toList());
        }

        @Override
        public boolean hasNext() {
            return location < noSpaceChildren.size();
        }

        @Override
        public EncapsulatingNode next() {
            EncapsulatingNode e = noSpaceChildren.get(location);
            ++location;
            return e;
        }

        public EncapsulatingNode value() {
            return noSpaceChildren.get(location);
        }


    }
}

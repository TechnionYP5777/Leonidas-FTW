package il.org.spartan.ispartanizer.plugin;

/**
 * Created by melanyc on 2/22/2017.
 */
public interface EncapsulatingNodeValueVisitor<T> {
    T visit(EncapsulatingNode e);
}

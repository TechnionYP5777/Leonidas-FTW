package il.org.spartan.Leonidas.plugin;

/**
 * @author michalcohen
 * @since 22-02-2017.
 */
public interface EncapsulatingNodeValueVisitor<T> {
    T visit(EncapsulatingNode e);
}

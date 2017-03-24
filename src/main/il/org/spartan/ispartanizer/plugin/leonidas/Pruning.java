package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiMethodCallExpression;
import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.EncapsulatingNode;
import il.org.spartan.ispartanizer.plugin.EncapsulatingNodeVisitor;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsi;

import java.util.Arrays;
import java.util.Optional;

import static il.org.spartan.ispartanizer.plugin.leonidas.KeyDescriptionParameters.ID;

/**
 * This class helps generate generic trees representing code template written
 * in the Leonidas Language.
 *
 * @author michalcohen
 * @since 07-01-2017
 */
public class Pruning {

    /**
     * Prunes all the stubs of the form "stub();" where "stub()"
     * is a method call for the method defined in GenericPsiElementStub.
     *
     * @param e - the root from which all such stubs are pruned
     */
    public static EncapsulatingNode prune(EncapsulatingNode e) {
        assert (e != null);
        e.accept(new EncapsulatingNodeVisitor() {
            @Override
            public void visit(EncapsulatingNode e) {
                if (!iz.methodCallExpression(e.getInner())) {
                    return;
                }
                PsiMethodCallExpression exp = az.methodCallExpression(e.getInner());

                Pruning.getStubName(e).ifPresent(y -> {
                    EncapsulatingNode prev = Pruning.getRealParent(e, y);
                    GenericPsi x = y.getGenericPsiType(exp, exp.getUserData(ID));
                    if (x != null) {
                        prev.replace(EncapsulatingNode.buildTreeFromPsi(x)); //replace the stub tree with the generic psi type tree
                    }
                });
            }
        });
        return e;
    }

    public static Optional<GenericPsiElementStub.StubName> getStubName(EncapsulatingNode e) {
        if (!iz.methodCallExpression(e.getInner()))
            return Optional.empty();
        PsiMethodCallExpression exp = az.methodCallExpression(e.getInner());

        return Arrays.stream(GenericPsiElementStub.StubName.values())
                .filter(x -> x.stubName().equals(exp.getMethodExpression().getText()))
                .findFirst(); //assuming there is only one enum value in StubName that fits the stub kind.
    }

    public static EncapsulatingNode getRealParent(EncapsulatingNode e, GenericPsiElementStub.StubName y) {
        EncapsulatingNode prev = e;
        EncapsulatingNode next = e.getParent();
        while (y.goUpwards(prev, next)) {
            prev = next;
            next = next.getParent();
        }
        return prev;
    }
}

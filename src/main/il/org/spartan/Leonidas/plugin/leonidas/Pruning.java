package il.org.spartan.Leonidas.plugin.leonidas;

import com.intellij.psi.PsiMethodCallExpression;
import il.org.spartan.Leonidas.auxilary_layer.az;
import il.org.spartan.Leonidas.auxilary_layer.iz;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;
import il.org.spartan.Leonidas.plugin.leonidas.GenericPsiTypes.GenericPsi;

import java.util.Arrays;
import java.util.Optional;

import static il.org.spartan.Leonidas.plugin.leonidas.KeyDescriptionParameters.ID;

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
     * @param n - the root from which all such stubs are pruned
     */
    public static EncapsulatingNode prune(EncapsulatingNode n) {
        assert (n != null);
        n.accept(e1 -> {
            if (!iz.methodCallExpression(e1.getInner()))
				return;
            PsiMethodCallExpression exp = az.methodCallExpression(e1.getInner());

            Pruning.getStubName(e1).ifPresent(y -> {
                EncapsulatingNode prev = Pruning.getRealParent(e1, y);
                GenericPsi x = y.getGenericPsiType(exp, exp.getUserData(ID));
                if (x != null)
                    prev.setInner(x);
                //prev.replace(EncapsulatingNode.buildTreeFromPsi(x));
            });
        });
        return n;
    }

    public static Optional<GenericPsiElementStub.StubName> getStubName(EncapsulatingNode n) {
        if (!iz.methodCallExpression(n.getInner()))
            return Optional.empty();
        PsiMethodCallExpression exp = az.methodCallExpression(n.getInner());

        return Arrays.stream(GenericPsiElementStub.StubName.values())
                .filter(x -> x.stubName().equals(exp.getMethodExpression().getText()))
                .findFirst(); //assuming there is only one enum value in StubName that fits the stub kind.
    }

    public static EncapsulatingNode getRealParent(EncapsulatingNode n, GenericPsiElementStub.StubName y) {
        EncapsulatingNode prev = n, next = n.getParent();
        for (; y.goUpwards(prev, next); next = next.getParent())
			prev = next;
        return prev;
    }
}

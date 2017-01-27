package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethodCallExpression;
import il.org.spartan.ispartanizer.auxilary_layer.Utils;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsi;

import java.util.Arrays;

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
    public static PsiElement prune(PsiElement e) {
        assert(e!=null);
        Utils.getChildrenOfType(e, PsiMethodCallExpression.class).forEach(exp -> {
            Arrays.stream(GenericPsiElementStub.StubName.values())
                    .filter(x -> x.stubName().equals(exp.getMethodExpression().getText()))
                    .findFirst() //assuming there is only one enum value in StubName that fits the stub kind.
                    .ifPresent(y -> {
                        PsiElement prev = exp;
                        for (PsiElement next = exp.getParent(); next.getText().startsWith(y.stubName()); ) {
                            prev = next;
                            next = next.getParent();
                        }
                        GenericPsi x = y.getGenericPsiType(exp, exp.getUserData(ID));
                        if (x != null)
                            prev.replace(x);
                    });
        });
        return e;
    }
}

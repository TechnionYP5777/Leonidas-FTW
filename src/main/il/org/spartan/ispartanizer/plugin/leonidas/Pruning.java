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
     * Prunes all the stubs from the form "stub();" where "stub()"
     * is a method call for the method defined in GenericPsiElementStub.
     *
     * @param e - the root from which all such stubs are pruned
     */
    public static PsiElement prune(PsiElement e) {
        Utils.getChildrenOfType(e, PsiMethodCallExpression.class).forEach(exp -> {
            Arrays.stream(GenericPsiElementStub.StubName.values())
                    .filter(x -> x.stubName().equals(exp.getMethodExpression().getText()))
                    .findFirst()
                    .ifPresent(y -> {
                        PsiElement prev = exp;
                        PsiElement next = exp.getParent();
                        while (next.getText().startsWith(y.stubName())) {
                            prev = next;
                            next = next.getParent();
                        }
                        GenericPsi x = y.getGenericPsiType(exp, exp.getUserData(ID));
                        if (x != null) {
                            prev.replace(x);
                        }
                    });
        });
        return e;
    }
}

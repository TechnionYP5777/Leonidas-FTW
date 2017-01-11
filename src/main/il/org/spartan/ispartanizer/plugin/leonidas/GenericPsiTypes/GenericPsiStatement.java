package il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiStatement;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.leonidas.KeyDescriptionParameters;

/**
 * Created by melanyc on 1/11/2017.
 */
public class GenericPsiStatement extends GenericPsi implements PsiStatement {

    public GenericPsiStatement(PsiElement inner) {
        super(inner);
    }


    @Override
    public String toString() {
        return "Generic statement" + inner.getUserData(KeyDescriptionParameters.ORDER);
    }

    @Override
    public boolean isOfGenericType(PsiElement e) {
        return iz.statement(e);
    }

    @Override
    public GenericPsiStatement copy() {
        // might not work because inner has no parent
        return new GenericPsiStatement(inner.copy());
    }
}

package il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes;

import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.leonidas.KeyDescriptionParameters;

/**
 * @author Roey Maor, Amir sagiv
 * @since 20-01-2017
 */
public class GenericPsiBlock extends GenericPsi{

    public GenericPsiBlock(PsiElement element) {
        super(element);
    }

    @Override
    public boolean generalizes(PsiElement e) {
        return iz.blockStatement(e) || iz.block(e) || iz.statement(e);
    }

    @Override
    public String toString() {
        return "Generic anyBlock" + inner.getUserData(KeyDescriptionParameters.ID);
    }

    @Override
    public GenericPsiBlock copy() {
        return new GenericPsiBlock(inner.copy());
    }
}

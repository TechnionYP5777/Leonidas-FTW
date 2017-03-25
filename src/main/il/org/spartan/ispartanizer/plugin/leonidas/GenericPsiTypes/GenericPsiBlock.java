package il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiInvalidElementAccessException;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.leonidas.KeyDescriptionParameters;
import org.jetbrains.annotations.NotNull;

/**
 * @author Roey Maor, Amir sagiv
 * @since 20-01-2017
 */
public class GenericPsiBlock extends GenericPsi{

    public GenericPsiBlock(PsiElement element) {
        super(element, "generic block");
    }

    @Override
    public boolean generalizes(PsiElement e) {
        return iz.blockStatement(e) || iz.block(e) || iz.statement(e);
    }

    @NotNull
    @Override
    public Project getProject() throws PsiInvalidElementAccessException {
        return inner.getProject();
    }

    @Override
    public String toString() {
        return "Generic anyBlock" + inner.getUserData(KeyDescriptionParameters.ID);
    }

    /*@Override
    public GenericPsiBlock copy() {
        return new GenericPsiBlock(inner.copy());
    }
    */
}

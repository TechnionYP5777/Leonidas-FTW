package il.org.spartan.leonidas.plugin.leonidas.GenericPsiTypes;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.tree.IElementType;
import il.org.spartan.leonidas.auxilary_layer.iz;
import il.org.spartan.leonidas.plugin.leonidas.KeyDescriptionParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author michalcohen
 * @since 11-01-2017
 */
public class GenericPsiExpression extends GenericPsi {
    PsiType t;

    public GenericPsiExpression(PsiType evalType, PsiElement e) {
        super(e, "generic expression");
        this.t = evalType;
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, @Nullable PsiElement lastParent, @NotNull PsiElement place) {
        return false;
    }

    @Override
    public String toString() {
        return "Generic expression" + inner.getUserData(KeyDescriptionParameters.ID);
    }

    public PsiType evaluationType() {
        return t;
    }

    @Override
    public boolean generalizes(PsiElement e) {
        return iz.expression(e);
    }

    @Override
    public IElementType getTokenType() {
        return getElementType();
    }

    /*@Override
    public GenericPsiExpression copy() {
        return new GenericPsiExpression(t, inner.copy());
    }
    */
}

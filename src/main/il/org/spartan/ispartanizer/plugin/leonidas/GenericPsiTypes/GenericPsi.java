package il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes;


import com.intellij.openapi.util.Key;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.JavaResolveCache;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.impl.source.tree.JavaElementType;
import com.intellij.psi.impl.source.tree.java.PsiBinaryExpressionImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by melanyc on 1/11/2017.
 */
public abstract class GenericPsi extends CompositePsiElement {

    final int myHC = CompositePsiElement.ourHC++;
    PsiElement inner;
    PsiFile containingFile;

    protected GenericPsi(PsiElement inner) {
        super(JavaElementType.DUMMY_ELEMENT);
        this.inner = inner;
        this.containingFile = inner.getContainingFile();

    }

    @Override
    public final int hashCode() {
        return myHC;
    }

    @Override
    public PsiFile getContainingFile() {
        return containingFile;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return inner.getUserData(key);
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {
        inner.putUserData(key, value);
    }

    public abstract boolean isOfGenericType(PsiElement e);

}

package il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes;


import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by melanyc on 1/11/2017.
 */
public abstract class GenericPsi extends CompositePsiElement {

    final int myHC = CompositePsiElement.ourHC++;
    PsiElement inner;

    protected GenericPsi(PsiElement inner) {
        super(JavaElementType.DUMMY_ELEMENT);
        this.inner = inner;

    }

    @Override
    public final int hashCode() {
        return myHC;
    }

    @Override
    public PsiFile getContainingFile() {
        return inner.getContainingFile();
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

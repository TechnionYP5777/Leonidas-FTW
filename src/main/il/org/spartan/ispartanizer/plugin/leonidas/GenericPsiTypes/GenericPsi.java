package il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes;


import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract class representing generic psi element.
 * @author michalcohen
 * @since 11-01-17
 */
public abstract class GenericPsi extends CompositePsiElement {

    final int myHC = ++CompositePsiElement.ourHC;
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
    public <T> T getUserData(@NotNull Key<T> ¢) {
        return inner.getUserData(¢);
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> t, @Nullable T value) {
        inner.putUserData(t, value);
    }

    /**
     * checks if a different element conforms with the represented generic type.
     *
     * @param e - the element to be checked
     * @return true iff e is of the generic type
     */
    public abstract boolean isOfGenericType(PsiElement e);

}

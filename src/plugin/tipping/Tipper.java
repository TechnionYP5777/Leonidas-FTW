package plugin.tipping;

import com.intellij.psi.PsiElement;

/**
 * @author Oren Afek
 * @author Michal Cohen
 * @since 2016.12.1
 */
public interface Tipper<T extends PsiElement> {

    boolean canTip(T element);

    default String description() {
        return getClass().getSimpleName();
    }

    String description(final T t);

    Tip tip(final T node);

    default Class<? extends PsiElement> getPsiClass() {
        return PsiElement.class;
    }

}

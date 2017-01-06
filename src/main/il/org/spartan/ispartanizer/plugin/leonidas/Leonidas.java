package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;

/**
 * @author Oren Afek
 * @since 06/01/17
 */
public @interface Leonidas {
    Class<? extends PsiElement> value() default PsiExpression.class;
}

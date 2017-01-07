package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;

/**
 * Annotation that annotates the methods "from" and "to" in a Leonidas
 *      file that helps the TreeBuilder extract the correct Psi tree
 *      from the method.
 * @author Oren Afek
 * @since 06/01/17
 */
public @interface Leonidas {
    Class<? extends PsiElement> value() default PsiExpression.class;
}

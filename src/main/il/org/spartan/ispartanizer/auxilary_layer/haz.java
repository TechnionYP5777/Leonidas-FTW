package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * Utils class that helps checking if a Psi tree has a specific component.
 * @author Michal Cohen
 * @since 01-12-2016
 */
public enum haz {
    ;

    public static boolean centVariableDefinition(final PsiElement e) {
        final Wrapper<Boolean> b = new Wrapper<>(Boolean.FALSE);
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitIdentifier(PsiIdentifier ¢) {
                super.visitIdentifier(¢);
                if (¢.getText().contains("¢"))
                    b.set(true);
            }
        });
        return b.inner;
    }

    public static boolean functionNamed(final PsiElement e, String name) {
        final Wrapper<Boolean> b = new Wrapper<>(Boolean.FALSE);
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethod(PsiMethod ¢) {
                super.visitMethod(¢);
                b.inner = !¢.getName().equals(name) ? b.inner : Boolean.TRUE;
            }
        });
        return b.inner;
    }

    public static boolean equalsOperator(PsiBinaryExpression ¢) {
        return ¢ != null && iz.equalsOperator(step.operator(¢));
    }

    public static boolean notEqualsOperator(PsiBinaryExpression ¢) {
        return ¢ != null && step.operator(¢).equals(JavaTokenType.NE);
    }

    public static boolean compilationErrors(PsiFile ¢) {
        return haz.syntaxErrors(¢) || CompilationCenter.hasCompilationErrors(¢);
    }

    public static boolean syntaxErrors(PsiElement ¢) {
        return (PsiTreeUtil.hasErrorElements(¢));
    }
}

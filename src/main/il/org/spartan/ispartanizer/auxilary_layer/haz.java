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

    public static boolean centVariableDefinition(final PsiElement p) {
        final Wrapper<Boolean> b = new Wrapper<>(Boolean.FALSE);
        p.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitIdentifier(PsiIdentifier e) {
                super.visitIdentifier(e);
                if (e.getText().contains("¢")) { //TODO there is a problem with cent representation...
                    b.set(true);
                }
            }
        });
        assert b.inner != null;
        return b.inner;
    }

    public static boolean functionNamed(final PsiElement p, String name) {
        final Wrapper<Boolean> b = new Wrapper<>(Boolean.FALSE);
        p.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethod(PsiMethod method) {
                super.visitMethod(method);
                b.inner = method.getName().equals(name) ? Boolean.TRUE : b.inner;
            }
        });
        assert b.inner != null;
        return b.inner;
    }

    public static boolean equalsOperator(PsiBinaryExpression expression) {
        return expression != null && iz.equalsOperator(step.operator(expression));
    }

    public static boolean notEqualsOperator(PsiBinaryExpression expression) {
        return expression != null && step.operator(expression).equals(JavaTokenType.NE);
    }

    public static boolean compilationErrors(PsiFile file){
        return haz.syntaxErrors(file) || CompilationCenter.hasCompilationErrors(file);
    }

    public static boolean syntaxErrors(PsiElement element) {
        return (PsiTreeUtil.hasErrorElements(element));
    }

    public static boolean body(PsiMethod m){
        return m != null && m.getBody() != null;
    }


}

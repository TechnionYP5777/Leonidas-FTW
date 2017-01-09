package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.*;
import il.org.spartan.ispartanizer.auxilary_layer.iz;

import java.util.Arrays;

/**
 * This class helps generate generic trees representing code template written
 * in the Leonidas Language.
 *
 * @author michalcohen
 * @since 7-1-2017
 */
public class Pruning {

    /**
     * Pruning All of the stubs
     * @param e JD
     * @return e after pruning
     */
    public static PsiElement pruneAll(PsiElement e){
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if(Arrays.stream(LeonidasTipper.StubName.values())
                        .noneMatch(stubName -> stubName.matchesStubName(expression))){
                    return;
                }
                expression.deleteChildRange(expression.getFirstChild(), expression.getLastChild());
            }
        });

        return e;
    }

    /**
     * Prunes all the trees of the conditions insides if statements
     *
     * @param e - the root from which all such conditions are pruned
     */
    public static PsiElement booleanExpression(PsiElement e) {
        e.accept(new JavaRecursiveElementVisitor() {

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression exp) {
                super.visitMethodCallExpression(exp);
                if (!exp.getMethodExpression().getText().equals(LeonidasTipper.StubName
                        .BOOLEAN_EXPRESSION.stubName())) {
                    return;
                }
                exp.deleteChildRange(exp.getFirstChild(), exp.getLastChild());
            }

        });

        return e;
    }

    /**
     * Prunes all the statements from the form "statement();" where "statement()"
     * is a method call for the method defined in LeonidasTipper.
     *
     * @param e - the root from which all such statements are pruned
     */
    public static PsiElement statements(PsiElement e) {
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (!expression.getMethodExpression().getText()
                        .equals(LeonidasTipper.StubName.STATEMENT.stubName())) {
                    return;
                }
                expression.deleteChildRange(expression.getFirstChild(), expression.getLastChild());
            }

        });

        return e;
    }

    /**
     * Pruns all the children of a PsiElement
     * @param e JD
     * @return e
     */
    public static PsiElement children(PsiElement e){
        e.deleteChildRange(e.getFirstChild(),e.getLastChild());
        return e;
    }

}

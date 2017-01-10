package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiStatement;
import il.org.spartan.ispartanizer.auxilary_layer.az;
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
     *
     * @param e JD
     * @return e after pruning
     */
    public static PsiElement pruneAll(PsiElement e) {
        statements(e);
        booleanExpression(e);
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
                if (!exp.getMethodExpression().getText().equals(GenericPsiElement.StubName
                        .BOOLEAN_EXPRESSION.stubName())) {
                    return;
                }
                exp.putUserData(KeyDescriptionParameters.GENERIC_NAME, GenericPsiElement.StubName.BOOLEAN_EXPRESSION.stubName());
                deleteChildren(exp);
            }

        });

        return e;
    }

    /**
     * Prunes all the statements from the form "statement();" where "statement()"
     * is a method call for the method defined in GenericPsiElement.
     *
     * @param e - the root from which all such statements are pruned
     */
    public static PsiElement statements(PsiElement e) {
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitStatement(PsiStatement statement) {
                super.visitStatement(statement);
                if (iz.expressionStatement(statement) && iz.methodCallExpression(statement.getFirstChild()) && az.methodCallExpression(statement.getFirstChild()).getMethodExpression().getText().equals(GenericPsiElement.StubName.STATEMENT.stubName())) {
                    statement.putUserData(KeyDescriptionParameters.GENERIC_NAME, GenericPsiElement.StubName.STATEMENT.stubName());
                    deleteChildren(statement);
                }
            }

        });

        return e;
    }

    /**
     * Pruns all the deleteChildren of a PsiElement
     *
     * @param e JD
     * @return e
     */
    public static PsiElement deleteChildren(PsiElement e) {
        e.deleteChildRange(e.getFirstChild(), e.getLastChild());
        return e;
    }

}

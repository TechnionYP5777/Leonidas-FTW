package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiStatement;
import il.org.spartan.ispartanizer.auxilary_layer.iz;

/**
 * This class helps generate generic trees representing code template written
 * in the Leonidas Language.
 *
 * @author michalcohen
 * @since 7-1-2017
 */
public class Pruning {

    /**
     * Prunes all the trees of the conditions insides if statements
     *
     * @param e - the root from which all such conditions are pruned
     */
    public static void ifCondition(PsiElement e) {
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitExpression(PsiExpression exp) {
                super.visitExpression(exp);
                if (!iz.ifStatement(exp.getParent())) {
                    return;
                }
                exp.deleteChildRange(exp.getFirstChild(), exp.getLastChild());
            }
        });
    }

    /**
     * Prunes all the statements from the form "statement();" where "statement()"
     * is a method call for the method defined in LeonidasTipper.
     *
     * @param e - the root from which all such statements are pruned
     */
    public static void statements(PsiElement e) {
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitStatement(PsiStatement statement) {
                super.visitStatement(statement);
                if (!statement.getText().equals("statement();")) {
                    return;
                }
                statement.deleteChildRange(statement.getFirstChild(), statement.getLastChild());
            }
        });
    }

}

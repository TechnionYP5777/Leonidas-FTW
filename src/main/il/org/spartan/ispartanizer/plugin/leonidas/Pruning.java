package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiType;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsiExpression;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsiStatement;

import static il.org.spartan.ispartanizer.plugin.leonidas.KeyDescriptionParameters.GENERIC_NAME;
import static il.org.spartan.ispartanizer.plugin.leonidas.KeyDescriptionParameters.ID;

/**
 * This class helps generate generic trees representing code template written
 * in the Leonidas Language.
 *
 * @author michalcohen
 * @since 07-01-2017
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
                if (!exp.getMethodExpression().getText().equals(GenericPsiElementStub.StubName
                        .BOOLEAN_EXPRESSION.stubName())) {
                    return;
                }
                PsiElement prev = exp;
                PsiElement next = exp.getParent();
                while (iz.expression(next) && next.getText().startsWith("booleanExpression")) {
                    prev = next;
                    next = next.getParent();
                }
                GenericPsiExpression x = new GenericPsiExpression(PsiType.BOOLEAN, prev);
                x.putUserData(GENERIC_NAME, GenericPsiElementStub.StubName.BOOLEAN_EXPRESSION.stubName());
                x.putUserData(ID, exp.getUserData(ID));
                prev.replace(x);
            }

        });

        return e;
    }

    /**
     * Prunes all the statements from the form "statement();" where "statement()"
     * is a method call for the method defined in GenericPsiElementStub.
     *
     * @param e - the root from which all such statements are pruned
     */
    public static PsiElement statements(PsiElement e) {
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression exp) {
                super.visitMethodCallExpression(exp);
                if (!exp.getMethodExpression().getText().equals(GenericPsiElementStub.StubName
                        .STATEMENT.stubName())) {
                    return;
                }
                PsiElement prev = exp;
                PsiElement next = exp.getParent();
                while (iz.statement(next) && !iz.enclosingStatement(next)) {
                    prev = next;
                    next = next.getParent();
                }
                GenericPsiStatement x = new GenericPsiStatement(prev);
                x.putUserData(GENERIC_NAME, GenericPsiElementStub.StubName.STATEMENT.stubName());
                x.putUserData(ID, exp.getUserData(ID));
                prev.replace(x);
            }

        });

        return e;
    }

}

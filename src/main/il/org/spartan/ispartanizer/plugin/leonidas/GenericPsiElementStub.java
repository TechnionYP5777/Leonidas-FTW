package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiType;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsi;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsiExpression;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsiStatement;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * This class defines methods that will represent generic structures of code
 * such as: statements, conditions, blocks, variable declaration and more.
 *
 * @author Oren Afek
 * @since 06-01-2017
 */
public abstract class GenericPsiElementStub {

    /**
     * method stub representing a boolean expression for leonidas tippers
     *
     * @param id the serial no to distinct between several boolean expressions in the same tipper
     * @return true
     */
    protected final boolean booleanExpression(int id) {
        return booleanExpression();
    }

    protected final boolean booleanExpression() {
        return true;
    }

    /**
     * method stub representing a statement for leonidas tippers
     *
     * @param id the serial no to distinct between several statements in the same tipper
     */
    protected final void statement(int id) {
    }

    protected final void statement() {
    }

    /**
     * method stub representing an identifier for leonidas tippers
     *
     * @param id the serial no to distinct between several identifiers in the same tipper
     * @return stub object
     */
    protected final Object identifier(int id) {
        return identifier();
    }

    protected final Object identifier() {
        return new Object();
    }

    /**
     * method stub representing an array identifier for leonidas tippers
     *
     * @param id the serial no to distinct between several array identifiers in the same tipper
     * @return stub array
     */
    protected final Object[] arrayIdentifier(int id) {
        return arrayIdentifier();
    }

    protected final Object[] arrayIdentifier() {
        return new Object[0];
    }

    /**
     * method stub representing an array identifier for leonidas tippers
     *
     * @param id the serial no to distinct between several array identifiers in the same tipper
     * @return stub array
     */
    protected final <T> Stream<T> streamMethodInvocations(int id) {
        return streamMethodInvocations();
    }

    protected final <T> Stream<T> streamMethodInvocations() {
        return Stream.of();
    }

    public enum StubName {
        BOOLEAN_EXPRESSION("booleanExpression"),
        STATEMENT("statement"),
        IDENTIFIER("identifier"),
        ARRAY_IDENTIFIER("arrayIdentifier");

        private String stubName;

        StubName(String stubName) {
            this.stubName = stubName;
        }

        public static StubName valueOf(PsiMethodCallExpression expression) {

            return Arrays.stream(values())
                    .filter(stub -> expression.getMethodExpression().getText().equals(stub.stubName()))
                    .findFirst().orElseGet(null);

        }

        public String stubName() {
            return stubName;
        }

        public String stubMethodCallExpression() {
            return String.format("%s()", stubName);
        }

        public String stubMethodCallExpressionStatement() {
            return String.format("%s();", stubName);
        }

        public boolean matchesStubName(PsiMethodCallExpression e) {
            return e.getMethodExpression().getText().equals(this.stubName);
        }

        public GenericPsi getGenericPsiType(PsiElement inner, Integer id) {
            GenericPsi x;
            switch (this) {
                case BOOLEAN_EXPRESSION:
                    x = new GenericPsiExpression(PsiType.BOOLEAN, inner);
                    break;
                case STATEMENT:
                    x = new GenericPsiStatement(inner);
                    break;
                default:
                    return null;
            }
            x.putUserData(KeyDescriptionParameters.ID, id);
            return x;
        }
    }
}

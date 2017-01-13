package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiMethodCallExpression;

/**
 * This class defines methods that will represent generic structures of code
 * such as: statements, conditions, blocks, variable declaration and more.
 * @author Oren Afek
 * @since 06-01-2017
 */
public abstract class GenericPsiElementStub {

    /**
     * method stub representing a boolean expression for leonidas tippers
     *
     * @param order the serial no to distinct between several boolean expressions in the same tipper
     * @return true
     */
    protected final boolean booleanExpression(int order) {
        return true;
    }

    /**
     * method stub representing a statement for leonidas tippers
     *
     * @param order the serial no to distinct between several statements in the same tipper
     */
    protected final void statement(int order) {
    }

    /**
     * method stub representing an identifier for leonidas tippers
     *
     * @param order the serial no to distinct between several identifiers in the same tipper
     * @return stub object
     */
    protected final Object identifier(int order) {
        return new Object();
    }

    /**
     * method stub representing an array identifier for leonidas tippers
     *
     * @param order the serial no to distinct between several array identifiers in the same tipper
     * @return stub array
     */

    protected final Object[] arrayIdentifier(int order) {
        return new Object[0];
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

        public static StubName valueOf(PsiMethodCallExpression expression){
            if(expression.getMethodExpression().getText().equals(BOOLEAN_EXPRESSION.stubName)){
                return BOOLEAN_EXPRESSION;
            }

            if(expression.getMethodExpression().getText().equals(STATEMENT.stubName)){
                return STATEMENT;
            }

            //TODO: ADD HERE MORE IF's UPON ADDING MORE TYPES !!!
            return null;
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

        public boolean matchesStubName(PsiMethodCallExpression e){
            return e.getMethodExpression().getText().equals(this.stubName);
        }
    }
}

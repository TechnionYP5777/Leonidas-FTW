package il.org.spartan.ispartanizer.plugin.leonidas;

/**
 * This class defines methods that will represent generic structures of code
 * such as: statements, conditions, blocks, variable declaration and more.
 *
 * @author Oren Afek
 * @since 06/01/17
 */
public abstract class LeonidasTipper {

    public enum StubName {
        BOOLEAN_EXPRESSION("booleanExpression"),
        STATEMENT("statement");

        private String stubName;

        StubName(String stubName) {
            this.stubName = stubName;
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
    }

    /**
     * method stub representing a boolean expression for leonidas tippers
     * @param order the serial no to distinct between several boolean expressions in the same tipper
     * @return true
     */
    protected final boolean booleanExpression(int order) {
        return true;
    }

    /**
     * method stub representing a statement for leonidas tippers
     * @param order the serial no to distinct between several statements in the same tipper
     */
    protected final void statement(int order) {
    }
}

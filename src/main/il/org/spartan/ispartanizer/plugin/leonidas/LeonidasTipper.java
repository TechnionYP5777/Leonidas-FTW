package il.org.spartan.ispartanizer.plugin.leonidas;

/**
 * This class defines methods that will represent generic structures of code
 * such as: statements, conditions, blocks, variable declaration and more.
 *
 * @author Oren Afek
 * @since 06/01/17
 */
public abstract class LeonidasTipper {

    enum StubName {
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

    protected final boolean booleanExpression() {
        return true;
    }

    protected final void statement() {
    }
}

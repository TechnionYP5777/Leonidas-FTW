package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.*;
import il.org.spartan.ispartanizer.tippers.TipperTest;

/**
 * Created by melanyc on 1/7/2017.
 */
public class PsiTreeMatcherTest extends TipperTest {

    public void testMatch1() {
        PsiExpression x = createTestExpressionFromString("x + 1");
        PsiExpression y = createTestExpressionFromString("x + 1");
        assertTrue(PsiTreeMatcher.match(x, y));
        assertTrue(PsiTreeMatcher.match(x, createTestExpressionFromString("y + 1")));
    }

    public void testMatch2() {
        PsiCodeBlock b = createTestCodeBlockFromString("{ int x = 5; }");
        b.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
        assertTrue(PsiTreeMatcher.match(b, createTestCodeBlockFromString("{ int y = 10; }")));
    }

    public void testMatch3() {
        PsiIfStatement b = createTestIfStatement("booleanExpression()", "statement();");
        b.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock ¢) {
                super.visitCodeBlock(¢);
                ¢.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression ¢) {
                super.visitMethodCallExpression(¢);
                ¢.putUserData(KeyDescriptionParameters.GENERIC_NAME, ¢.getMethodExpression().getText());
            }
        });

        Pruning.prune(b);

        assertTrue(PsiTreeMatcher.match(b, createTestIfStatement("true && false", " if (true) { int y = 5; } ")));
    }

    public void testMatch4() {
        PsiIfStatement b = createTestIfStatement("booleanExpression()", "statement();");
        b.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock ¢) {
                super.visitCodeBlock(¢);
                ¢.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression ¢) {
                super.visitMethodCallExpression(¢);
                ¢.putUserData(KeyDescriptionParameters.GENERIC_NAME, ¢.getMethodExpression().getText());
            }
        });

        Pruning.prune(b);

        assertTrue(PsiTreeMatcher.match(b, createTestIfStatement("true", " int y = 5; ")));
    }
}
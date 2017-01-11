package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.*;
import il.org.spartan.ispartanizer.auxilary_layer.Wrapper;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.tippers.TipperTest;

import java.util.Map;

/**
 * Created by melanyc on 1/7/2017.
 */
public class PsiTreeMatcherTest extends TipperTest {

    public void testMatch1() {
        PsiExpression x = createTestExpressionFromString("x + 1");
        PsiExpression y = createTestExpressionFromString("x + 1");
        assertTrue(PsiTreeMatcher.match(x, y));
        PsiExpression z = createTestExpressionFromString("y + 1");
        assertTrue(PsiTreeMatcher.match(x, z));
    }

    public void testMatch2() {
        PsiCodeBlock b = createTestCodeBlockFromString("{ int x = 5; }");
        b.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
        b.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitStatement(PsiStatement statement) {
                super.visitStatement(statement);
                statement.deleteChildRange(statement.getFirstChild(), statement.getLastChild());
            }
        });
        PsiCodeBlock y = createTestCodeBlockFromString("{ int y = 10; }");
        assertTrue(PsiTreeMatcher.match(b, y));
    }

    public void testMatch3() {
        PsiIfStatement b = createTestIfStatement("booleanExpression()", "statement();");
        b.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock block) {
                super.visitCodeBlock(block);
                block.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression methodCallExpression) {
                super.visitMethodCallExpression(methodCallExpression);
                methodCallExpression.putUserData(KeyDescriptionParameters.GENERIC_NAME, methodCallExpression.getMethodExpression().getText());
            }
        });

        Pruning.pruneAll(b);

        PsiIfStatement y = createTestIfStatement("true && false", " if (true) { int y = 5; } ");
        assertTrue(PsiTreeMatcher.match(b, y));
    }

    public void testMatch4() {
        PsiIfStatement b = createTestIfStatement("booleanExpression()", "statement();");
        b.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock block) {
                super.visitCodeBlock(block);
                block.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression methodCallExpression) {
                super.visitMethodCallExpression(methodCallExpression);
                methodCallExpression.putUserData(KeyDescriptionParameters.GENERIC_NAME, methodCallExpression.getMethodExpression().getText());
            }
        });

        Pruning.pruneAll(b);

        PsiIfStatement y = createTestIfStatement("true", " int y = 5; ");
        assertTrue(PsiTreeMatcher.match(b, y));
    }

    public void testExtractInfo1() {
        PsiIfStatement b = createTestIfStatement("booleanExpression()", "statement();");
        Wrapper<Integer> count = new Wrapper<>(0);
        b.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock block) {
                super.visitCodeBlock(block);
                block.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression methodCallExpression) {
                super.visitMethodCallExpression(methodCallExpression);
                methodCallExpression.putUserData(KeyDescriptionParameters.GENERIC_NAME, methodCallExpression.getMethodExpression().getText());
                methodCallExpression.putUserData(KeyDescriptionParameters.ORDER, count.get());

                count.set(count.get() + 1);
            }
        });

        Pruning.pruneAll(b);

        PsiIfStatement y = createTestIfStatement("true", " int y = 5; ");
        assertTrue(PsiTreeMatcher.match(b, y));
        Map<Integer, PsiElement> m = PsiTreeMatcher.extractInfo(b, y);
        assertTrue(iz.expression(m.get(0)));
        assertTrue(iz.statement(m.get(1)));
        System.out.println(m);
    }
}
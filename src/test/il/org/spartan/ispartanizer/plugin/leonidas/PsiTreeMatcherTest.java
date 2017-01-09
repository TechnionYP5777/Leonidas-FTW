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
        });

        Pruning.pruneAll(b);

        PsiIfStatement y = createTestIfStatement("true", " int y = 5; ");
        assertTrue(PsiTreeMatcher.match(b, y));
    }

}
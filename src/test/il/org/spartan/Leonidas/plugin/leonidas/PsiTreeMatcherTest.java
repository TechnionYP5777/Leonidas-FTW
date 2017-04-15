package il.org.spartan.Leonidas.plugin.leonidas;

import com.intellij.psi.*;
import il.org.spartan.Leonidas.PsiTypeHelper;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;

/**
 * @author michalcohen
 * @since 07-01-2017.
 */
public class PsiTreeMatcherTest extends PsiTypeHelper {

    public void testMatch1() {
        PsiExpression x = createTestExpressionFromString("x + 1");
        PsiExpression y = createTestExpressionFromString("x + 1");
        assertTrue(PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(x), EncapsulatingNode.buildTreeFromPsi(y)));
        PsiExpression z = createTestExpressionFromString("y + 1");
        assertFalse(PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(x), EncapsulatingNode.buildTreeFromPsi(z)));
    }

    public void testMatch2() {
        PsiCodeBlock b = createTestCodeBlockFromString("{ int x = 5; }");
        b.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
        PsiCodeBlock y = createTestCodeBlockFromString("{ int y = 10; }");
        assertFalse(PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(b), EncapsulatingNode.buildTreeFromPsi(y)));
    }

    public void testMatch3() {
        PsiIfStatement b = createTestIfStatement("booleanExpression(0)", "statement(1);");
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
        EncapsulatingNode encapsulatingNode = EncapsulatingNode.buildTreeFromPsi(b);
        Pruning.prune(encapsulatingNode);

        PsiIfStatement y = createTestIfStatement("true && false", " if (true) { int y = 5; } ");
        assertTrue(PsiTreeMatcher.match(encapsulatingNode, EncapsulatingNode.buildTreeFromPsi(y)));
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
        EncapsulatingNode n = EncapsulatingNode.buildTreeFromPsi(b);
        Pruning.prune(n);

        PsiIfStatement y = createTestIfStatement("true", " int y = 5; ");
        assertTrue(PsiTreeMatcher.match(n, EncapsulatingNode.buildTreeFromPsi(y)));
    }
}
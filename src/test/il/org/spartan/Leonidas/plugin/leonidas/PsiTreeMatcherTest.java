package il.org.spartan.Leonidas.plugin.leonidas;

import il.org.spartan.Leonidas.PsiTypeHelper;

/**
 * @author michalcohen
 * @since 07-01-2017.
 */
public class PsiTreeMatcherTest extends PsiTypeHelper {
/*
    public void testMatch1() {
        PsiExpression x = createTestExpressionFromString("x + 1"), y = createTestExpressionFromString("x + 1");
        assert PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(x), EncapsulatingNode.buildTreeFromPsi(y));
        assert !PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(x),
				EncapsulatingNode.buildTreeFromPsi(createTestExpressionFromString("y + 1")));
    }

    public void testMatch2() {
        PsiCodeBlock b = createTestCodeBlockFromString("{ int x = 5; }");
        b.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
        assert !PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(b),
				EncapsulatingNode.buildTreeFromPsi(createTestCodeBlockFromString("{ int y = 10; }")));
    }

    public void testMatch3() {
        PsiIfStatement b = createTestIfStatement("booleanExpression(0)", "statement(1);");
        b.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock b) {
                super.visitCodeBlock(b);
                b.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression x) {
                super.visitMethodCallExpression(x);
                x.putUserData(KeyDescriptionParameters.GENERIC_NAME, x.getMethodExpression().getText());
            }
        });
        EncapsulatingNode encapsulatingNode = EncapsulatingNode.buildTreeFromPsi(b);
        Pruning.prune(encapsulatingNode);

        assert PsiTreeMatcher.match(encapsulatingNode, EncapsulatingNode
				.buildTreeFromPsi(createTestIfStatement("true && false", " if (true) { int y = 5; } ")));
    }


    public void testMatch4() {
        PsiIfStatement b = createTestIfStatement("booleanExpression()", "statement();");
        b.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock b) {
                super.visitCodeBlock(b);
                b.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression x) {
                super.visitMethodCallExpression(x);
                x.putUserData(KeyDescriptionParameters.GENERIC_NAME, x.getMethodExpression().getText());
            }
        });
        EncapsulatingNode n = EncapsulatingNode.buildTreeFromPsi(b);
        Pruning.prune(n);

        assert PsiTreeMatcher.match(n,
				EncapsulatingNode.buildTreeFromPsi(createTestIfStatement("true", " int y = 5; ")));
    }*/
}
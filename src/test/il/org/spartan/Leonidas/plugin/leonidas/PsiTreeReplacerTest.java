package il.org.spartan.Leonidas.plugin.leonidas;

import il.org.spartan.Leonidas.PsiTypeHelper;

/**
 * @author AnnaBel7
 * @since 09/01/2017.
 */
public class PsiTreeReplacerTest extends PsiTypeHelper {

    /*private static final String TIPPER_FILE_NAME_1 = "RemoveCurlyBracesFromIfStatement.java";
    private static final String TIPPER_FILE_NAME_2 = "RemoveCurlyBracesFromWhileStatement.java";
    private static final String TIPPER_FILE_NAME_3 = "IfDoubleNot.java";
    private static final String COND = "x==0";
    private static final String THEN = "return x;";
    private static final PsiTreeReplacer PSI_TREE_REPLACER = new PsiTreeReplacer();

    // TODO: uncomment test and fix/replace if needed
    public void testGetReplacer1() {
        PsiIfStatement ifStatement = createTestIfStatement(COND, THEN);
        PsiTreeTipperBuilder tipperBuilder = null;
        try {
            tipperBuilder = new PsiTreeTipperBuilderImpl().buildTipperPsiTree(TIPPER_FILE_NAME_1);
        } catch (Exception ignore) {
        }
        PsiRewrite rewrite = new PsiRewrite().project(ifStatement.getProject()).psiFile(ifStatement.getContainingFile());
        PsiIfStatement newIfStatement = createTestIfStatementNoBraces(COND, THEN);
        assertTrue(PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(PsiTreeReplacer.getReplacer(ifStatement, tipperBuilder, rewrite)), EncapsulatingNode.buildTreeFromPsi(newIfStatement)));
        assertFalse(PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(PsiTreeReplacer.getReplacer(ifStatement, tipperBuilder, rewrite)), EncapsulatingNode.buildTreeFromPsi(ifStatement)));
    }*/

    //TODO: FAIL SINCE SPARTANIZATION
//    public void testGetReplacer2() {
//        PsiWhileStatement whileStatement = createTestWhileStatementFromString("while(" + COND + "){" + THEN + "}");
//        PsiTreeTipperBuilder tipperBuilder = null;
//        try {
//            tipperBuilder = new PsiTreeTipperBuilderImpl().buildTipperPsiTree(TIPPER_FILE_NAME_2);
//        } catch (Exception ignore) {
//        }
//        assert tipperBuilder != null;
//        PsiRewrite rewrite = new PsiRewrite().project(whileStatement.getProject()).psiFile(whileStatement.getContainingFile());
//        PsiWhileStatement newWhileStatement = createTestWhileStatementFromString("while(" + COND + ")" + THEN);
//        PsiElement n = PsiTreeReplacer.getReplacer(whileStatement, tipperBuilder, rewrite);
//        assert PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(n),
//				EncapsulatingNode.buildTreeFromPsi(newWhileStatement));
//        assert !PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(n),
//				EncapsulatingNode.buildTreeFromPsi(whileStatement));
//    }

    //TODO: FAIL SINCE SPARTANIZATION
//    public void testGetReplace3() {
//        PsiIfStatement ifStatement = createTestIfStatement("!(!(" + COND + "))", THEN);
//        PsiTreeTipperBuilder tipperBuilder = null;
//        try {
//            tipperBuilder = new PsiTreeTipperBuilderImpl().buildTipperPsiTree(TIPPER_FILE_NAME_3);
//        } catch (Exception ignore) {
//        }
//        assert tipperBuilder != null;
//        PsiRewrite rewrite = new PsiRewrite().project(ifStatement.getProject()).psiFile(ifStatement.getContainingFile());
//        PsiIfStatement newIfStatement = createTestIfStatement(COND, THEN);
//        PsiElement n = PsiTreeReplacer.getReplacer(ifStatement, tipperBuilder, rewrite);
//        assert PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(n),
//				EncapsulatingNode.buildTreeFromPsi(newIfStatement));
//        assert !PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(n),
//				EncapsulatingNode.buildTreeFromPsi(ifStatement));
//    }

    // TODO: uncomment test and fix/replace if needed
    /*   public void testReplace() {
        /*PsiIfStatement ifStatement = createTestIfStatement(COND, THEN);
        PsiElement parent = ifStatement.getParent();
        PsiTreeTipperBuilder tipperBuilder = null;
        try {
            tipperBuilder = new PsiTreeTipperBuilderImpl().buildTipperPsiTree(TIPPER_FILE_NAME_1);
        } catch (Exception ignore) {
        }
        PsiRewrite rewrite = new PsiRewrite().project(ifStatement.getProject()).psiFile(ifStatement.getContainingFile());
        PsiIfStatement newIfStatement = createTestIfStatementNoBraces(COND, THEN);
        EncapsulatingNode x = PsiTreeReplacer.replace(ifStatement, tipperBuilder, rewrite);
        EncapsulatingNode y = EncapsulatingNode.buildTreeFromPsi(newIfStatement);
        assertTrue(PsiTreeMatcher.match(x, y));
        assertTrue(PsiTreeMatcher.match(EncapsulatingNode.buildTreeFromPsi(PsiTreeUtil.findChildOfType(parent, PsiIfStatement.class)), EncapsulatingNode.buildTreeFromPsi(newIfStatement)));
    }


    public void testExtractInfo1() {
        PsiIfStatement b = createTestIfStatement("booleanExpression()", "statement();");
        Wrapper<Integer> count = new Wrapper<>(0);
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
                x.putUserData(KeyDescriptionParameters.ID, count.get());

                count.set(count.get() + 1);
            }
        });
        EncapsulatingNode n = EncapsulatingNode.buildTreeFromPsi(b);
        Pruning.prune(n);

        PsiIfStatement y = createTestIfStatement("true", " int y = 5; ");
        assert PsiTreeMatcher.match(n, EncapsulatingNode.buildTreeFromPsi(y));
        Map<Integer, PsiElement> m = PsiTreeReplacer.extractInfo(n, y);
        assert iz.expression(m.get(0));
        assert iz.statement(m.get(1));
    }*/

}
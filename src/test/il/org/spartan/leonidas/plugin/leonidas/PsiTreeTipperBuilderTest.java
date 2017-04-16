package il.org.spartan.leonidas.plugin.leonidas;

import il.org.spartan.leonidas.PsiTypeHelper;

/**
 * @author Oren Afek
 * @since 08/01/17
 */
public class PsiTreeTipperBuilderTest extends PsiTypeHelper {

    private static final String TEST_FILE_NAME = "RemoveCurlyBracesFromIfStatement" + ".java";

    private PsiTreeTipperBuilder $;

    // TODO: uncomment test and fix/replace if needed
    public void testBuildFromTestFileTree() throws Exception {
        /*$ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            EncapsulatingNode actualFrom = $.getFromPsiTree();
            assertTrue(iz.ifStatement(actualFrom.getInner()));
        } catch (IOException ignore) {
            fail();
        }*/
    }

    // TODO: uncomment test and fix/replace if needed
    public void testPuttingUserData() throws Exception {
        /*$ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            EncapsulatingNode actualFrom = $.getFromPsiTree();
            actualFrom.accept(e -> {
                if (iz.methodCallExpression(e.getInner()))
                    assertEquals(Integer.valueOf(0), e.getInner().getUserData(KeyDescriptionParameters.ID));
            });
        } catch (IOException ignore) {
            fail();
        }*/
    }

    // TODO: uncomment test and fix/replace if needed
    public void testPruning() throws Exception {
        /*$ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            EncapsulatingNode actualFrom = $.getFromPsiTree();
            actualFrom.accept(e -> {
                if (iz.methodCallExpression(e.getInner()))
                    assertEquals(0, e.getChildren().size());
            });
        } catch (IOException ignore) {
            fail();
        }*/
    }

    // TODO: uncomment test and fix/replace if needed
    public void testBuildToTestFileTree() throws Exception {
        /*$ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            EncapsulatingNode actualTo = $.getToPsiTree();
            assertTrue(iz.ifStatement(actualTo.getInner()));
        } catch (IOException ignore) {
            fail();
        }*/
    }

    // TODO: uncomment test and fix/replace if needed
    public void testGetRootElementType() throws Exception {
        /*$ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            assertEquals(PsiIfStatement.class, $.getRootElementType());
        } catch (IOException ignore) {
            fail();
        }*/
    }

    // TODO: uncomment test and fix/replace if needed
    public void testGetDescription() throws Exception {
        /*$ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            assertEquals("Remove redundent curly braces".trim(), $.getDescription());
        } catch (IOException ignore) {
            fail();
        }*/
    }
}

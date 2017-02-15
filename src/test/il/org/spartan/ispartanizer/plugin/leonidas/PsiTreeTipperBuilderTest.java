package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIfStatement;
import com.intellij.psi.PsiMethodCallExpression;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.tippers.TipperTest;

import java.io.IOException;

/**
 * @author Oren Afek
 * @since 08/01/17
 */
public class PsiTreeTipperBuilderTest extends TipperTest {

    private static final String TEST_FILE_NAME = "RemoveCurlyBracesFromIfStatement" + ".java";

    private PsiTreeTipperBuilder $;


    public void testBuildFromTestFileTree() throws Exception {
        $ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            PsiElement actualFrom = $.getFromPsiTree();
            assertTrue(iz.ifStatement(actualFrom));
        } catch (IOException ignore) {
            fail();
        }

    }

    public void testPuttingUserData() throws Exception {
        $ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            PsiElement actualFrom = $.getFromPsiTree();
            actualFrom.accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                    assertEquals(Integer.valueOf(0), expression.getUserData(KeyDescriptionParameters.ID));
                }
            });
        } catch (IOException ignore) {
            fail();
        }
    }

    public void testPruning() throws Exception {
        $ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            PsiElement actualFrom = $.getFromPsiTree();
            actualFrom.accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                    assertEquals(0, expression.getChildren().length);
                }
            });
        } catch (IOException ignore) {
            fail();
        }
    }

    public void testBuildToTestFileTree() throws Exception {
        $ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            PsiElement actualTo = $.getToPsiTree();
            assertTrue(iz.ifStatement(actualTo));
        } catch (IOException ignore) {
            fail();
        }
    }

    public void testGetRootElementType() throws Exception {
        $ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            assertEquals(PsiIfStatement.class, $.getRootElementType());
        } catch (IOException ignore) {
            fail();
        }
    }

    public void testGetDescription() throws Exception {
        $ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            assertEquals("Remove redundent curly braces".trim(), $.getDescription());
        } catch (IOException ignore) {
            fail();
        }
    }
}

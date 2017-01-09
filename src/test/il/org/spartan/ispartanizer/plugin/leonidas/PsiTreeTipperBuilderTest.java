package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
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

    public void testBuildTestFileTree() {

        $ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            PsiElement actualFrom = $.getFromPsiTree();
            assertTrue(iz.ifStatement(actualFrom));
        } catch (IOException ignore) {
            fail();
        }

    }

    public void testPuttingUserData() {
        $ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            PsiElement actualFrom = $.getFromPsiTree();
            actualFrom.accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                    assertEquals(Integer.valueOf(0), expression.getUserData(KeyDescriptionParameters.ORDER));
                }
            });
        } catch (IOException ignore) {
            fail();
        }
    }

    public void testPruning(){
        $ = new PsiTreeTipperBuilderImpl();
        try {
            $.buildTipperPsiTree(TEST_FILE_NAME);
            PsiElement actualFrom = $.getFromPsiTree();
            actualFrom.accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                    assertEquals(0,expression.getChildren().length);
                }
            });
        } catch (IOException ignore) {
            fail();
        }
    }
}

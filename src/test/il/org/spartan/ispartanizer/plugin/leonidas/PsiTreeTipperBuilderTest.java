package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.tippers.TipperTest;

import java.io.IOException;

import static il.org.spartan.ispartanizer.plugin.leonidas.LeonidasTipper.StubName.*;

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
            $.buildTipperPsiTree(TEST_FILE_NAME, getProject());
            PsiElement actualFrom = $.getFromPsiTree();
            assertTrue(iz.ifStatement(actualFrom));
        } catch (IOException ignore) {
            fail();
        }


    }
}

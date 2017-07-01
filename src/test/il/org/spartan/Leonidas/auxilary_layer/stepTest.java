package il.org.spartan.Leonidas.auxilary_layer;

import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import il.org.spartan.Leonidas.PsiTypeHelper;
import org.junit.Assert;

/**
 * @author RoeiRaz
 * @since 10-01-2017
 */
public class stepTest extends PsiTypeHelper {
    public void testDocCommentA() {
        Assert.assertEquals(step.docCommentString(createTestClassFromString("", "A", "", "public")), "");
    }

    public void testDocCommentB() {
        Assert.assertEquals(step.docCommentString(getClassInFile(createTestFileFromString("/**javadoc*/class A {}"))),
				"javadoc");
    }

    public void testDocCommentC() {
        Assert.assertEquals(
				step.docCommentString(createTestClassFromString("", "A", "/** javadoc */void foo(){}", "public")), "");
    }

    public void testFirstParameter() {
        PsiParameter parameter = step.firstParameter(createTestMethodFromString("public void foo(int a, int b, int c) {}").getParameterList());

        assertNotNull(parameter);
        Assert.assertEquals(parameter.getType(), PsiType.INT);
        Assert.assertEquals(parameter.getName(), "a");

        Assert.assertNull(step.secondParameter(createTestMethodFromString("public void foo() {}").getParameterList()));
    }

    public void testSecondParameter() {
        PsiParameter parameter = step.secondParameter(createTestMethodFromString("public void foo(int a, int b, int c) {}").getParameterList());

        assertNotNull(parameter);
        Assert.assertEquals(parameter.getType(), PsiType.INT);
        Assert.assertEquals(parameter.getName(), "b");

        Assert.assertNull(step.secondParameter(createTestMethodFromString("public void foo(int a) {}").getParameterList()));
    }
}

package il.org.spartan.leonidas.auxilary_layer;

import il.org.spartan.leonidas.PsiTypeHelper;
import org.junit.Assert;

// TODO @OrenAfek @RoeiRaz refactor this, stepTest shouldn't extend PsiTypeHelper because it isn't a tipper..

/**
 * @author RoeiRaz
 * @since 2017
 */
public class stepTest extends PsiTypeHelper {
    public void testDocCommentA() {
        String className = "A";
        String doc = "";
        Assert.assertEquals(step.docCommentString(createTestClassFromString("", className, "", "public")), doc);
    }

    public void testDocCommentB() {
        String src = "/**javadoc*/class A {}";
        String doc = "javadoc";
        Assert.assertEquals(step.docCommentString(getClassInFile(createTestFileFromString(src))), doc);
    }

    public void testDocCommentC() {
        String className = "A";
        String classBody = "/** javadoc */void foo(){}";
        String doc = "";
        Assert.assertEquals(step.docCommentString(createTestClassFromString("", className, classBody, "public")), doc);
    }
}

package il.org.spartan.ispartanizer.auxilary_layer;

import il.org.spartan.ispartanizer.tippers.TipperTest;
import org.junit.Assert;

// TODO @OrenAfek @RoeiRaz refactor this, stepTest shouldn't extend TipperTest because it isn't a tipper..

/**
 * @author RoeiRaz
 * @since 2017
 */
public class stepTest extends TipperTest {
    public void testDocCommentA() {
        String src = "class A {}";
        String doc = "";
        Assert.assertEquals(step.docCommentString(createTestClassFromString(src)), doc);
    }

    public void testDocCommentB() {
        String src = "/**javadoc*/class A {}";
        String doc = "javadoc";
        Assert.assertEquals(step.docCommentString(getClassInFile(createTestFileFromString(src))), doc);
    }

    public void testDocCommentC() {
        String src = "class A {/** javadoc */void foo() {}}";
        String doc = "";
        Assert.assertEquals(step.docCommentString(createTestClassFromString(src)), doc);
    }
}

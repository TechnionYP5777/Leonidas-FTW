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
}

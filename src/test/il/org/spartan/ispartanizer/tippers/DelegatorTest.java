package il.org.spartan.ispartanizer.tippers;

import il.org.spartan.ispartanizer.plugin.tippers.Delegator;
import org.junit.Assert;

/**
 * @author RoeiRaz
 * @since 2017
 */
public class DelegatorTest extends TipperTest {
    public void testA() {
        Assert.assertTrue(new Delegator().canTip(createTestMethodFromString("void foo() {boo();}")));
    }

    public void testB() {
        Assert.assertTrue(new Delegator().canTip(createTestMethodFromString("void foo(int x) {boo(x);}")));
    }

    public void testC() {
        Assert.assertFalse(new Delegator().canTip(createTestMethodFromString("void foo(int y) {boo(y + 1);}")));
    }

    public void testD() {
        Assert.assertFalse(new Delegator().canTip(createTestMethodFromString("int foo(int z) {return rect.bar(z);}")));
    }

    public void testE() {
        Assert.assertFalse(new Delegator().canTip(createTestMethodFromString("int foo(int z) {return z.bar(z);}")));
    }

    public void testF() {
        Assert.assertTrue(new Delegator().canTip(createTestMethodFromString("int foo(int z) {return bar(z, 1);}")));
    }

    public void testG() {
        Assert.assertEquals(new Delegator()
                        .createReplacement(createTestMethodFromString("int foo(int z) {return bar(z, 1);}")).getText(),
                ("/**" + new Delegator().tag() + "*/" + "int foo(int z) {return bar(z, 1);}"));
    }

    public void testH() {
        Assert.assertEquals(
                new Delegator().createReplacement(createTestMethodFromString("void foo(int x) {boo(x);}")).getText(),
                ("/**" + new Delegator().tag() + "*/" + "void foo(int x) {boo(x);}"));
    }
}

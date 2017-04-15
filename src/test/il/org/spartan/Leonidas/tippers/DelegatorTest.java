package il.org.spartan.Leonidas.tippers;

import com.intellij.psi.PsiMethod;
import il.org.spartan.Leonidas.PsiTypeHelper;
import il.org.spartan.Leonidas.plugin.tippers.Delegator;
import org.junit.Assert;

/**
 * @author RoeiRaz
 * @since 2017
 */
public class DelegatorTest extends PsiTypeHelper {
    public void testA() {
        PsiMethod method = createTestMethodFromString("void foo() {boo();}");
        Assert.assertTrue(new Delegator().canTip(method));
    }

    public void testB() {
        PsiMethod method = createTestMethodFromString("void foo(int x) {boo(x);}");
        Assert.assertTrue(new Delegator().canTip(method));
    }

    public void testC() {
        PsiMethod method = createTestMethodFromString("void foo(int y) {boo(y + 1);}");
        Assert.assertFalse(new Delegator().canTip(method));
    }

    public void testD() {
        PsiMethod method = createTestMethodFromString("int foo(int z) {return rect.bar(z);}");
        Assert.assertFalse(new Delegator().canTip(method));
    }

    public void testE() {
        PsiMethod method = createTestMethodFromString("int foo(int z) {return z.bar(z);}");
        Assert.assertFalse(new Delegator().canTip(method));
    }

    public void testF() {
        PsiMethod method = createTestMethodFromString("int foo(int z) {return bar(z, 1);}");
        Assert.assertTrue(new Delegator().canTip(method));
    }

    public void testG() {
        String from = "int foo(int z) {return bar(z, 1);}";
        String to = "/**" + new Delegator().tag() + "*/" + from;
        PsiMethod method = createTestMethodFromString(from);
        Assert.assertEquals(new Delegator().createReplacement(method).getText(), to);
    }

    public void testH() {
        String from = "void foo(int x) {boo(x);}";
        String to = "/**" + new Delegator().tag() + "*/" + from;
        PsiMethod method = createTestMethodFromString(from);
        Assert.assertEquals(new Delegator().createReplacement(method).getText(), to);
    }
}

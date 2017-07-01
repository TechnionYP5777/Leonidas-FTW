package il.org.spartan.Leonidas.auxilary_layer;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiDeclarationStatementImpl;
import il.org.spartan.Leonidas.PsiTypeHelper;
import org.junit.Assert;

import java.util.List;

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

    public void testArguments() {
        assertNull(step.arguments(null));
        String arg1 = "ami", arg2 = "tami", arg3 = "liyami";
        List<PsiExpression> arguments = step.arguments(createTestMethodCallExpression("f", arg1, arg2, arg3));
        assertEquals(3, arguments.size());
        assertEqualsByText(arg1, arguments.get(0));
        assertEqualsByText(arg2, arguments.get(1));
        assertEqualsByText(arg3, arguments.get(2));

    }

    public void testParameters() {
        String methodCode = "public void foo(int a, int b, int c) { }";

        List<PsiParameter> params = step.parameters(createTestMethodFromString(methodCode));
        assertEquals(3, params.size());
        assertEqualsByText("int a", params.get(0));
        assertEqualsByText("int b", params.get(1));
        assertEqualsByText("int c", params.get(2));

        params = step.parameters(createTestMethodFromString(methodCode).getParameterList());
        assertEquals(3, params.size());
        assertEqualsByText("int a", params.get(0));
        assertEqualsByText("int b", params.get(1));
        assertEqualsByText("int c", params.get(2));
    }

    public void testFields() {
        String classCode = "public class X { private String s; protected final X x; public void foo() { } }";
        List<PsiField> fields = step.fields(createTestClassFromString(classCode));
        assertEquals(2, fields.size());
        assertEqualsByText("private String s;", fields.get(0));
        assertEqualsByText("protected final X x;", fields.get(1));
    }

    public void testNextSibling() {
        String blockCode = "{ int x = 4; \n\r\t\r\r\r\r\r\n     \n \n \r int y = 6;}";
        PsiElement sibling = step.nextSibling(createTestCodeBlockFromString(blockCode).getFirstBodyElement());
        assertEquals(PsiDeclarationStatementImpl.class, sibling.getClass());
        assertEqualsByText("int x = 4;", sibling);
        assertEqualsByText("int y = 6;", step.nextSibling(sibling));
    }

    public void testHighestParent() {
        String blockCode = "void f(int y) { int x = 4; }";
        assertEqualsByText("int y", step.getHighestParent(step.firstParameter(createTestMethodFromString(blockCode))));
    }
}

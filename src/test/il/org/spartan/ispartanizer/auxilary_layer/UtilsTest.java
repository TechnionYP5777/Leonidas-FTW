package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import com.intellij.testFramework.PsiTestCase;
import il.org.spartan.ispartanizer.tippers.TipperTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Roey Maor, michal cohen
 * @since 12/22/2016.
 */
public class UtilsTest extends TipperTest {
    static final String dummyFileName = "test.java";

    public void testin() throws Exception {
        List<Integer> aList = new ArrayList<>();
        aList.addAll(Arrays.asList(55,44,33,22,11));
        assertTrue(Utils.in(44,aList.toArray()));
        assertFalse(Utils.in(43,aList.toArray()));
        List<Integer> emptyList = new ArrayList<>();
        assertFalse(Utils.in(0,emptyList.toArray()));
        assertFalse(Utils.in(null,aList));
        assertFalse(Utils.in(null,emptyList.toArray()));
        assertFalse(Utils.in(40,null));
    }

    public void testfindClass() throws Exception {
        /*
            the assert statement is used as a latch to check if it is in a parent class or not
         */
        String classExistsSource1 = "package test; "+
                "public class Test { "+
                "public Test() { "+
                "final int x=3; assert(true); System.out.println(\"lalala\"); "+
                "} "+
                "}";

        PsiFile classExistsFile1 = createFile(dummyFileName,classExistsSource1);
        PsiAssertStatement assertInSource1 = Utils.getChildrenOfType(classExistsFile1, PsiAssertStatement.class).get(0);

        String classExistsSource2 =
                "public Test() { "+
                "final int x=3; assert(true); System.out.println(\"lalala\"); "+
                "} ";

        PsiMethod methodIn2 = createTestMethodFromString(classExistsSource2);

        assertTrue(Utils.findClass(assertInSource1) != null);
        assertTrue(Utils.findClass(assertInSource1).getName().equals("Test"));
        assertTrue(Utils.findClass(methodIn2) == null);

    }

    public void testfindMethodByName() throws Exception {

    }

    public void testgetCurrentClass() throws Exception {

    }

    public void testconforms() throws Exception {

    }

    public void testgetAllReferences() throws Exception {

    }

    public void testvisitRecursive() throws Exception {

    }

}
package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
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
        String classExistsSource1 = "package test; " +
                "public class Test { " +
                "public Test() { " +
                "final int x=3; assert(true); System.out.println(\"lalala\"); " +
                "} " +
                "}";

        PsiFile classExistsFile1 = createFile(dummyFileName, classExistsSource1);
        PsiAssertStatement assertInSource1 = Utils.getChildrenOfType(classExistsFile1, PsiAssertStatement.class).get(0);

        String classExistsSource2 =
                "public Test() { " +
                        "final int x=3; assert(true); System.out.println(\"lalala\"); " +
                        "} ";

        PsiMethod methodIn2 = createTestMethodFromString(classExistsSource2);

        assertTrue(Utils.findClass(assertInSource1) != null);
        assertTrue("Test".equals(Utils.findClass(assertInSource1).getName()));
        assertTrue(Utils.findClass(methodIn2) == null);
        assertTrue(Utils.findClass(null) == null);
    }

    public void testfindMethodByName() throws Exception {
        String source1 =  "public class Test{ "+
                "public Test() { "+
                "final int x=3; assert(true); System.out.println(\"lalala\"); "+
                "} "+
                "}";

        String source2 = "public class Test2{ "+
                            "double d = 3.0;" +
                            "}";

        String source3 =  "public class Test{ "+
                "public mish() { "+
                "} "+
                "public mumkin() {"+
                "} "+
                "}";

        PsiClass class1 = createTestClassFromString(source1);
        PsiClass class2 = createTestClassFromString(source2);
        PsiClass class3 = createTestClassFromString(source3);

        assertTrue(Utils.findMethodByName(class1,"Test") != null);
        assertTrue(Utils.findMethodByName(class1,"") == null);
        assertTrue(Utils.findMethodByName(class2,"Test2") == null);
        assertTrue(Utils.findMethodByName(class3,"mish") != null);
        assertTrue(Utils.findMethodByName(class3,"mumkin") != null);
        assertTrue(Utils.findMethodByName(null,"mumkin") == null);

    }

    public void testgetAllReferences() throws Exception {
        PsiIdentifier id = createTestIdentifierFromString("id");
        PsiIdentifier nonExistant = createTestIdentifierFromString("ahgrtjygk");
        String m1String = "void foo(){ int k=5; }";
        PsiMethod m1 = createTestMethodFromString(m1String);
        String m2String = "int id() { int id=7; return 8;}";
        PsiMethod m2 = createTestMethodFromString(m2String);
        String m3String = "int id() { int id=7; return id;}";
        PsiMethod m3 = createTestMethodFromString(m3String);
        assertTrue(Utils.getAllReferences(null, null).isEmpty());
        assertTrue(Utils.getAllReferences(null, id).isEmpty());
        assertTrue(Utils.getAllReferences(m1, id).isEmpty());
        assertTrue(Utils.getAllReferences(m2,id).size() == 1);
        assertTrue(Utils.getAllReferences(m2, nonExistant).isEmpty());
        assertTrue(Utils.getAllReferences(m3,id).size() == 2);
    }

    public void ignoretestgetCurrentClass() throws Exception {
        //Cannot be tested here, requires real editor with active moving caret
    }

    public void ignoretestconforms() throws Exception {
        //conforms is currently unused
    }



}
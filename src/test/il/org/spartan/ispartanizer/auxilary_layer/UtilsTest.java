package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.testFramework.PsiTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Roey Maor, michal cohen
 * @since 12/22/2016.
 */
public class UtilsTest extends PsiTestCase {

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
package il.org.spartan.ispartanizer.tippers;

import il.org.spartan.ispartanizer.auxilary_layer.CompilationCenter;
import il.org.spartan.ispartanizer.auxilary_layer.haz;

/**
 * Created by Roey Maor on 1/4/2017.
 */
public class OptimisticTest extends TipperTest {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        CompilationCenter.initialize();
    }

    public void testDetectsCompilationErrors() throws Exception {
        assertTrue(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return (5=!=5);}")));
        assertTrue(haz.compilationErrors(createTestFileFromString(("package test; " + "public class Test { "
                + "public Test() { " + "final int x=3; x=5; System.out.println(\"lalala\"); " + "} " + "}"))));
    }

    public void testNoCompilationErrors(){
        assertFalse(haz.syntaxErrors(createTestExpressionFromString("5 == 5")));
        assertFalse(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return false;}")));
        assertFalse(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return (5==5);}")));
        assertFalse(haz.compilationErrors(createTestFileFromString(("package test; " + "public class Test { "
                + "public Test() { " + "final int x=3; System.out.println(\"lalala\"); " + "} " + "}"))));

    }

}

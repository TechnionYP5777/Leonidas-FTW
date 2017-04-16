package il.org.spartan.leonidas.tippers;

import il.org.spartan.leonidas.PsiTypeHelper;
import il.org.spartan.leonidas.auxilary_layer.CompilationCenter;
import il.org.spartan.leonidas.auxilary_layer.haz;

/**
 * @author Roey Maor
 * @since 04-01-2017.
 */
public class OptimisticTest extends PsiTypeHelper {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        CompilationCenter.initialize();
    }

    public void testDetectsCompilationErrors() throws Exception {
        assertTrue(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return (5=!=5);}")));
        //changing a final variable error:
        String source1 = "package test; "+
                "public class Test { "+
                "public Test() { "+
                "final int x=3; x=5; System.out.println(\"banana\"); " +
                "} "+
                "}";

        assertTrue(haz.compilationErrors(createTestFileFromString(source1)));
    }

    public void testNoCompilationErrors(){
        assertFalse(haz.syntaxErrors(createTestExpressionFromString("5 == 5")));
        assertFalse(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return false;}")));
        assertFalse(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return (5==5);}")));
        String source2 = "package test; "+
                "public class Test { "+
                "public Test() { "+
                "final int x=3; System.out.println(\"lalala\"); "+
                "} "+
                "}";
        assertFalse(haz.compilationErrors(createTestFileFromString(source2)));

    }

}

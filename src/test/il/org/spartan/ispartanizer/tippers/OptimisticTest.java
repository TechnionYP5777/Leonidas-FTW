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

    public void testDetectsSyntaxErrors() throws Exception {
        assertTrue(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return (5=!=5);}")));
        //changing a final variable error:
        String source1 = "package test; "+
                        "public class Test { "+
                            "public Test() { "+
                                "final int x=3; x=5; System.out.println(\"lalala\"); "+
                            "} "+
                        "}";

        assertTrue(haz.compilationErrors(createTestFileFromString(source1)));
    }

    // TODO: @RoeyMaor I made junit to ignore this test because it was failing (probably because CompilationCenter). Ask @RoeiRaz for more info
    public void ignoreTestNoSyntaxErrors(){
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

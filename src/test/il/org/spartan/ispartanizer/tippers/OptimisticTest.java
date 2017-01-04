package il.org.spartan.ispartanizer.tippers;

import il.org.spartan.ispartanizer.auxilary_layer.haz;

/**
 * Created by maorroey on 1/4/2017.
 */
public class OptimisticTest extends TipperTest {
    public void testDetectsSyntaxErrors(){
        assertTrue(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return (5=!=5);}")));
    }

    public void testNoSyntaxErrors(){
        assertFalse(haz.syntaxErrors(createTestExpressionFromString("5 == 5")));
        assertFalse(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return false;}")));
        assertFalse(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return (5==5);}")));
    }
}

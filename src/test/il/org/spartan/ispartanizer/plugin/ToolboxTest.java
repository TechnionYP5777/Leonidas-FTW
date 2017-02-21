package il.org.spartan.ispartanizer.plugin;

import il.org.spartan.ispartanizer.tippers.TipperTest;

/**
 * @author Michal Cohen
 * @since 21-02-2017
 */
public class ToolboxTest extends TipperTest {
    public void testIsElementOfOperableType() throws Exception {
        Toolbox instance = Toolbox.getInstance();
        assertFalse(instance.isElementOfOperableType(createTestNullExpression()));
        assertTrue(instance.isElementOfOperableType(createTestMethodFromString("int banana() { return 5;}")));
    }

}
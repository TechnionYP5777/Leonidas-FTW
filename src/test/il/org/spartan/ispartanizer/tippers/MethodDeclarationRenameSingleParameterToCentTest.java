package il.org.spartan.ispartanizer.tippers;

import il.org.spartan.ispartanizer.PsiTypeHelper;
import il.org.spartan.ispartanizer.plugin.tippers.MethodDeclarationRenameSingleParameterToCent;

/**
 * @author amirsagiv
 * @since 04-01-2017.
 */
public class MethodDeclarationRenameSingleParameterToCentTest extends PsiTypeHelper {

    public void testConstructorFails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("A(int x){this.x = x;}")));
    }

    public void testHasFirstParam¢Fails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("int setX(int ¢){this.x = ¢;}")));
    }

    public void testHasFirstParam$Fails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("int setX(int $){this.x = $;}")));
    }

    public void testHasFirstParam_Fails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("int setX(int _){this.x = _;}")));
    }

    public void testHasFirstParam__Fails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("int setX(int __){this.x = __;}")));
    }

    public void testAlreadyDefined¢Fails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("int setX(int x){int ¢ = 0; this.x = x;}")));
    }

    public void testMainMethodFails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("public static void main(String [] args){}")));
    }

    public void testMoreThanASingleParamFails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("int setX(int x1, int x2){ this.x = x1 == 0 ? x2:x1;}")));
    }

    public void testNoParamsFails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("int getX(){return this.x;}")));
    }

    public void testAbstractFails() {
        assertFalse(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("abstract int setX(int x);")));
    }

    public void testLegalSingleParamMethod() {
        assertTrue(new MethodDeclarationRenameSingleParameterToCent().canTip(createTestMethodFromString("int setX(int x){this.x = x;}")));
    }







}

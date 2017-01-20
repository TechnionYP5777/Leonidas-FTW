package il.org.spartan.ispartanizer.tippers;

import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.plugin.leonidas.PsiTreeTipperBuilder;
import il.org.spartan.ispartanizer.plugin.tippers.LeonidasTipper;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

/**
 * @author Oren Afek
 * @since 20/01/17
 */
public class LeonidasTipperTest extends TipperTest {

    private LeonidasTipper $;
    private PsiTreeTipperBuilder builderMock = Mockito.mock(PsiTreeTipperBuilder.class);
    private File fileMock = Mockito.mock(File.class);
    private Class<? extends PsiElement> testClassType = PsiConditionalExpression.class;
    private String statement = "System.out.println(\"Hello, World!\")";
    private String expression = "69 > 69 ? 69 : 69";
    private String testFileName = "testFileName.java";
    private String mockDescription = "This is just for fun. TEAM 8 IS THE BEST!!!";

    public LeonidasTipperTest() {
        configureMocks();
    }

    private void configureMocks() {
        Mockito.when(fileMock.getName()).thenReturn(testFileName);
        try {
            Mockito.when(builderMock.buildTipperPsiTree(fileMock.getName())).thenReturn(builderMock);
        } catch (IOException ignore) {
        }


    }

    private LeonidasTipper createTestObject() {
        configureMocks();
        return new LeonidasTipper(builderMock, fileMock);
    }

    public void testBuilderExceptionToIncreaseCoverage() throws Exception {
        PsiTreeTipperBuilder b = Mockito.mock(PsiTreeTipperBuilder.class);
        Mockito.when(b.buildTipperPsiTree(testFileName)).thenThrow(IOException.class);
        $ = new LeonidasTipper(b, fileMock);

    }

    public void testCanTip() {
        $ = createTestObject();
        Mockito.when(builderMock.getFromPsiTree()).thenReturn(createTestStatementFromString(statement));
        assertTrue($.canTip(createTestStatementFromString(statement)));
    }

    public void testCanNotTip() {
        $ = createTestObject();
        Mockito.when(builderMock.getFromPsiTree()).thenReturn(createTestStatementFromString(statement));
        assertFalse($.canTip(createTestExpressionFromString(expression)));
    }

    public void testGetPsiClass() {
        $ = createTestObject();
        Mockito.when(builderMock.getRootElementType()).then(x -> testClassType);
        assertEquals(testClassType, $.getPsiClass());
    }


    public void testDescription() {
        $ = createTestObject();
        assertEquals(testFileName, $.description());
    }

    public void testDescriptionOfElement() {
        $ = createTestObject();
        Mockito.when(builderMock.getDescription()).thenReturn(mockDescription);
        assertEquals(mockDescription, $.description(createTestExpressionFromString(expression)));
    }


}

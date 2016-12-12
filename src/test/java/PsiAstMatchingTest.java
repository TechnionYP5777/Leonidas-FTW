import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

//TODO @RoeiRaz finish writing this
//TODO @RoeiRaz We are required to use JUnit3 here. check if there is a way to use JUnit4.

/**
 * @author RoeiRaz
 */
public class PsiAstMatchingTest extends LightCodeInsightFixtureTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testA() {
        String src = "class A {}";
        PsiFile file = myFixture.configureByText(StdFileTypes.JAVA, src);
        System.out.println(file.getText());
    }
}

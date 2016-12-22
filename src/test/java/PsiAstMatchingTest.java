import com.intellij.psi.PsiFile;
import com.intellij.testFramework.PsiTestCase;

//TODO @RoeiRaz We are required to use JUnit3 here. check if there is a way to use JUnit4.

/**
 * @author RoeiRaz
 */
public class PsiAstMatchingTest extends PsiTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testA() throws Exception {
        // TODO @amirsagiv83 this is how you can create PsiFile without 'running' intellij.
        PsiFile file = createFile("foo.java", "class A{}");
        System.out.println(file.getText());
    }
}

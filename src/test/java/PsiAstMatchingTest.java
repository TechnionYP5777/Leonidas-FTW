import bridge.PsiAstMatching;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.PsiTestCase;
import utils.Wrapper;

//TODO @RoeiRaz We are required to use JUnit3 here. check if there is a way to use JUnit4.

/**
 * @author RoeiRaz
 */
public class PsiAstMatchingTest extends PsiTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testClass() throws Exception {
        PsiFile file = createFile("foo.java", "class A{}");
        PsiAstMatching m = new PsiAstMatching(file);
        PsiElement root = file.getNode().getPsi();
        Wrapper<Integer> count = new Wrapper<>(0);
        root.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitClass(PsiClass aClass) {
                super.visitClass(aClass);
                count.inner++;
                assert (m.getMapping().get(aClass).getClass().equals(org.eclipse.jdt.core.dom.TypeDeclaration.class));
            }
        });
        assert (count.inner == 1);
        //System.out.println(file.getText());
    }
}

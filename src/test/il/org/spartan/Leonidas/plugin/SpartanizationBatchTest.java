package il.org.spartan.Leonidas.plugin;

import com.intellij.openapi.progress.ProgressManager;
import com.intellij.psi.PsiFile;
import il.org.spartan.Leonidas.PsiTypeHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author RoeiRaz
 * @since 19/06/17
 */
public class SpartanizationBatchTest extends PsiTypeHelper {

    // TODO put in a resource file @RoeiRaz
    String file0 = "class A { void foo() { if (true) { System.out.println('hi'); } } }";

    public void testSpartanizationOfOneElementNotThrowingException() throws InterruptedException {
        PsiFile psiFile = createTestFileFromString(file0);
        List<PsiFile> files = Arrays.asList(psiFile);
        ProgressManager.getInstance().run(new SpartanizationBatch(getProject(), new HashSet<>(files)));
    }
}
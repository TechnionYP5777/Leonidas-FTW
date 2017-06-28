package il.org.spartan.Leonidas.plugin.GUI;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

/**
 * @author Anna Belozovsky
 * @since 28/06/2017
 */
public class TippersViewTest extends LightPlatformCodeInsightFixtureTestCase {
    String fileContent = "void func(int x){\nif(x==0){x=1;}\nwhile(x==0){x=1;}\n}";

    public void testView() {
//        PsiFile psiFile = PsiFileFactory.getInstance(Utils.getProject()).createFileFromText(JavaLanguage.INSTANCE, fileContent);
//        TippersView tippersView = new TippersView(psiFile);
//        tippersView.dispose();
    }

}
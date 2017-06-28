package il.org.spartan.Leonidas.plugin.GUI;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import il.org.spartan.Leonidas.plugin.GUI.About.AboutLeonidas;

/**
 * Created by roym on 28/06/17.
 */
public class AboutTest extends LightPlatformCodeInsightFixtureTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testAppearence() {
//        AboutAction aa = new AboutAction();
//        aa.actionPerformed(null);
        AboutLeonidas aboutLeonidas = new AboutLeonidas();
        aboutLeonidas.dispose();
    }
}
package il.org.spartan.ispartanizer.plugin.GUI.PlaygroundController;


import com.intellij.psi.PsiFile;
import com.intellij.testFramework.PsiTestCase;
import il.org.spartan.ispartanizer.plugin.Spartanizer;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * @author Anna Belozovsky
 * @since 05/04/2017
 * <p>
 * This is a controller class for the PlaygroundAction GUI
 */
public class PlaygroundServicesController extends PsiTestCase {

    private static final String fileName = "test.java";
    public TextArea inputCode;
    public Text outputCode;
    public Button spartanizeButton;
    public Button clearButton;

    public PlaygroundServicesController() {
        setName(fileName);
        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears all text related fields in the gui
     */
    public void onClearButtonClicked() {
        inputCode.setText("");
        outputCode.setText("");
    }

    /**
     * Applies spartanization on given code
     */
    public void onSpartanizeButtonClicked() {
        PsiFile file = createDummyFile(fileName, "public class foo{ public void func() {" + inputCode.getText() + "}}");

        Spartanizer.spartanizeFileOnePass(file);
        outputCode.setText(file.getText().replace("class foo { public void func(){", "").replace("}}", ""));
    }
}

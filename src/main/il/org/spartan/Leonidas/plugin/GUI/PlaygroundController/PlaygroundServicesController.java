package il.org.spartan.Leonidas.plugin.GUI.PlaygroundController;


import com.intellij.testFramework.PsiTestCase;
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

    int i = 0;

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
//        inputCode.setText("");
        outputCode.setText("");
    }

    /**
     * Applies spartanization on given code
     */
    public void onSpartanizeButtonClicked() {
        outputCode.setText(inputCode.getText());
//        outputCode.setText("public class foo{ public void func() {" + inputCode.getText() + "}}");
//        PsiFile file = createDummyFile(fileName, "public class foo{ public void func() {" + inputCode.getText() + "}}");
//        if (i % 2 == 0) {
//            outputCode.setText(file.getText());
//        } else {
//        System.out.println("before:");
//        System.out.println(file.getText());
//            Spartanizer.spartanizeFileOnePass(file);
//        System.out.println("after:");
//        System.out.println(file.getText());
//            outputCode.setText(file.getText());
//        }
//        i++;
    }
}

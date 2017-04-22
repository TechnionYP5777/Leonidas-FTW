package il.org.spartan.Leonidas.plugin.GUI.PlaygroundController;


import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.testFramework.PsiTestCase;
import com.sun.javafx.application.PlatformImpl;
import il.org.spartan.Leonidas.auxilary_layer.Utils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Anna Belozovsky
 * @since 05/04/2017
 * <p>
 * This is a controller class for the PlaygroundAction GUI
 *
 * this is not used for now
 *
 */
public class PlaygroundServicesController extends Application {

    private static final String fileName = "test.java";
    public TextArea inputCode;
    public Text outputCode;
    public Button spartanizeButton;
    public Button clearButton;
//    static PsiFile psiFile;

    int i = 0;

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
//        PsiFile file = PsiFileFactory.getInstance(Utils.getProject())
//                .createFileFromText(JavaLanguage.INSTANCE, inputCode.getText());
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

    @Override
    public void start(Stage primaryStage) {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/Playground.fxml"));
//        loader.setClassLoader(getClass().getClassLoader());
//        Parent root = (Parent) loader.load();
//        primaryStage.setTitle("Spartanizer Playground");
//        primaryStage.setScene(new Scene(root, 600, 600));
//        primaryStage.setResizable(false);
//        primaryStage.show();

        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/Playground.fxml"));
//            primaryStage.setTitle("Spartanizer Playground");
//            primaryStage.setResizable(false);
//            primaryStage.setScene(new Scene(root, 600, 600));
//            primaryStage.show();

            primaryStage.setTitle("Spartanizer Playground");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Playground.fxml"));
            loader.setClassLoader(this.getClass().getClassLoader());
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 600);
//                fxPanel.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

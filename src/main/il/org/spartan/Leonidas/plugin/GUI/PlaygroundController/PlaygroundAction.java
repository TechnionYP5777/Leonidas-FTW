package il.org.spartan.Leonidas.plugin.GUI.PlaygroundController;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sun.javafx.application.PlatformImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Anna Belozovsky
 * @since 09/04/2017
 */
public class PlaygroundAction extends AnAction {
    //    final JFXPanel fxPanel = new JFXPanel();
    Stage stage;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // create JavaFX scene
        PlatformImpl.startup(() -> {
            Parent root;
            try {
                stage = new Stage();
                stage.setTitle("Spartanizer Playground");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Playground.fxml"));
                loader.setClassLoader(this.getClass().getClassLoader());
                root = loader.load();
                Scene scene = new Scene(root, 600, 600);
//                fxPanel.setScene(scene);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}

package il.org.spartan.Leonidas.plugin.GUI.PlaygroundController;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Anna Belozovsky
 * @since 09/04/2017
 *
 * this is not used for now
 *
 */
public class PlaygroundAction extends AnAction {
    Stage stage;
    static boolean first = true;
    static PlaygroundServicesController controller;

//    @Override
//    public void actionPerformed(AnActionEvent e) {
//        // create JavaFX scene
//        PlatformImpl.startup(() -> {
//            Parent root;
//            try {
//                stage = new Stage();
//                stage.setTitle("Spartanizer Playground");
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Playground.fxml"));
//                loader.setClassLoader(this.getClass().getClassLoader());
//                root = loader.load();
//                Scene scene = new Scene(root, 600, 600);
////                fxPanel.setScene(scene);
//                stage.setResizable(false);
//                stage.setScene(scene);
//                stage.show();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
//    }

    @Override
    public void actionPerformed(final AnActionEvent event) {
        Platform.setImplicitExit(false);
        if (first) {
            PlatformImpl.startup(new Runnable() {
                @Override
                public void run() {
                    initialize();
                }
            });
            first = false;
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Your class that extends Application
                    initialize();
                }
            });
        }
    }

    private void initialize() {
        try {
            PlaygroundServicesController controller = new PlaygroundServicesController();
            if (stage == null) {
                stage = new Stage();
            }
            controller.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

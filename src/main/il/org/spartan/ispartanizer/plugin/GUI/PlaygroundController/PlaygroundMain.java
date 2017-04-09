package il.org.spartan.ispartanizer.plugin.GUI.PlaygroundController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Anna Belozovsky
 * @since 05/04/2017
 */
public class PlaygroundMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Playground.fxml"));
        primaryStage.setTitle("Spartanizer Playground");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}

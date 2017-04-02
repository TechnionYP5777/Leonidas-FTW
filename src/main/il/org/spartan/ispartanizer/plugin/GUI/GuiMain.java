package il.org.spartan.ispartanizer.plugin.GUI;

import com.intellij.codeInsight.template.postfix.templates.SoutPostfixTemplate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Amir Sagiv
 * @since 25/3/17
 *
 * This is the main class for the GUI
 */
public class GuiMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       //getClass().getResource("SpartanizerServices.fxml");
        Parent root = FXMLLoader.load(getClass().getResource("/SpartanizerServices.fxml"));
        primaryStage.setTitle("Spartanizer");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }


//    public static void main(String[] args) {
//        launch(args);
//    }
}
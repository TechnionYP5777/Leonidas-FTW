package il.org.spartan.ispartanizer.plugin.GUI;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import kotlin.reflect.jvm.internal.impl.util.Check;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Amir Sagiv
 * @since 2/4/17
 *
 * This is the controller class for the GUI
 */
public class SpartanizerServicesController implements Initializable {


    public VBox tipperList;
    public Button SelectAllButton;
    public Button CleanAllButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> tippers = new ArrayList<>();
        tippers.add("tip1");
        tippers.add("tip2");
        tippers.add("tip3");
        for(String s : tippers){
            tipperList.getChildren().add(new CheckBox(s));
        }
        SelectAllButton.setOnAction(event -> {
            tipperList.getChildren().forEach(cb -> ((CheckBox)cb).setSelected(true));

        });
        CleanAllButton.setOnAction(event -> {
            tipperList.getChildren().forEach(cb -> ((CheckBox)cb).setSelected(false));
        });

    }
}

package il.org.spartan.ispartanizer.plugin.GUI;

import il.org.spartan.ispartanizer.plugin.Toolbox;
import il.org.spartan.ispartanizer.plugin.leonidas.Leonidas;
import il.org.spartan.ispartanizer.plugin.tippers.LeonidasTipper;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    public Label description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<LeonidasTipper> tippers = Toolbox.getAllTippers();

        for(LeonidasTipper tipper : tippers){
            tipperList.getChildren().add(new CheckBox(tipper.getClass().getName()));
           // tipperList.getChildren().add(new CheckBox("tip"));
        }
        tipperList.getChildren().add(new CheckBox("tip"));

        SelectAllButton.setOnAction(event -> {
            tipperList.getChildren().forEach(cb -> ((CheckBox)cb).setSelected(true));

        });
        CleanAllButton.setOnAction(event -> {
            tipperList.getChildren().forEach(cb -> ((CheckBox)cb).setSelected(false));
        });

        tipperList.getChildren().forEach(tip ->{
            tip.setOnMouseEntered(event -> {
                description.setText("hello!");
            });
            tip.setOnMouseExited(event -> {
                description.setText("");
            });
        });

    }
}

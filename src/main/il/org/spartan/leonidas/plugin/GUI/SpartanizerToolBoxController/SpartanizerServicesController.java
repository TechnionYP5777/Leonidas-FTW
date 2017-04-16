package il.org.spartan.leonidas.plugin.GUI.SpartanizerToolBoxController;

import il.org.spartan.leonidas.plugin.Toolbox;
import il.org.spartan.leonidas.plugin.tippers.LeonidasTipper;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

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

    /**
     * this method is responsible of controlling the gui and defining be
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<LeonidasTipper> tippers = Toolbox.getAllTippers();
        Map<String, LeonidasTipper> tippersMap = createMapFromTipperList(tippers);


        // creating the checkBox list.
        for(LeonidasTipper tipper : tippers){
            tipperList.getChildren().add(new CheckBox(tipper.getClass().getName()));
        }
        //TODO: remove when done.
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
                //description.setText((tippersMap.get(((CheckBox)tip).getText())).description());
            });
            tip.setOnMouseExited(event -> {
                description.setText("");
            });
        });

    }


    private Map<String,LeonidasTipper> createMapFromTipperList(List<LeonidasTipper> list){
        Map<String,LeonidasTipper> tipperMap = new HashMap<>();
        list.forEach(tip -> {
            tipperMap.put(tip.getClass().getName(),tip);
        });

        return tipperMap;
    }
}

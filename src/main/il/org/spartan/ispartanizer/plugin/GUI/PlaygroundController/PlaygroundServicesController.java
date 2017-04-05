package il.org.spartan.ispartanizer.plugin.GUI.PlaygroundController;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Anna Belozovsky
 * @since 05/04/2017
 * <p>
 * This is a controller class for the Playground GUI
 */
public class PlaygroundServicesController implements Initializable {

    public TextArea inputCode;
    public Text outputCode;
    public Button spartanizeButton;
    public Button clearButton;

    /**
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

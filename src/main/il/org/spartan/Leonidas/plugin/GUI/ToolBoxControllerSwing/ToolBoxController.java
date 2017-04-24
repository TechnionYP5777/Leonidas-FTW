package il.org.spartan.Leonidas.plugin.GUI.ToolBoxControllerSwing;




import javax.swing.*;
import java.awt.*;


/**
 * Created by Amir on 24-04-2017.
 */
public class ToolBoxController extends JFrame{
    private JPanel mainPanel;
    private JButton applyButton;
    private JButton clearAllButton;
    private JButton selectAllButton;
    private JSplitPane splitPanel;
    private JPanel rightPannel;
    private JPanel infoPanel;
    private JPanel buttonsPanel;
    private JScrollPane tippersPane;
    //private JList list1;


    public ToolBoxController() {
        super("Spartanizer ToolBox Controller");

        CheckBoxList list1 = new CheckBoxList();
        list1.addCheckbox(new JCheckBox("tip1!"));
        list1.addCheckbox(new JCheckBox("tip2!"));
        list1.addCheckbox(new JCheckBox("tip3!"));
        tippersPane.setViewportView(list1);
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(600, 600));
        setResizable(false);
        pack();
        setVisible(true);

    }



}



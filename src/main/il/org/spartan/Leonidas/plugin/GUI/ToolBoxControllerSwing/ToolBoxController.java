package il.org.spartan.Leonidas.plugin.GUI.ToolBoxControllerSwing;




import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


/**
 * @author Amir Sagiv
 * @since 24/04/2017
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
    private JTextArea textArea1;

    private CheckBoxList list;


    public ToolBoxController() {
        super("Spartanizer ToolBox Controller");
        list = new CheckBoxList();
        JCheckBox cb = new JCheckBox("hei");
        list.addCheckbox(new JCheckBox("tip1!"));
        list.addCheckbox(new JCheckBox("tip2!"));
        list.addCheckbox(new JCheckBox("tip3!"));

        list.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());

                if (index > -1 && index < list.getNumOfElements()) {
                    JCheckBox checkbox = (JCheckBox)
                            list.getModel().getElementAt(index);
                    textArea1.setText("info of "+checkbox.getText());
                }
            }
        });


        tippersPane.setViewportView(list);
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(600, 600));
        setResizable(false);
        pack();
        setVisible(true);
        selectAllButton.addActionListener(e->selectAllListener());
        clearAllButton.addActionListener(e -> clearAllListener());

    }

    private void checkBoxListener(JCheckBox cb) {
        infoPanel.add(new JLabel("poop"));
    }

    private void clearAllListener() {
        list.setAllClear();
    }

    private void selectAllListener() {
        list.setAllSelected();
    }




}



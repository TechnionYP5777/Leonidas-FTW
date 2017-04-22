package il.org.spartan.Leonidas.plugin.GUI.PlaygroundSwing;

import com.intellij.lang.java.JavaLanguage;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import il.org.spartan.Leonidas.auxilary_layer.Utils;
import il.org.spartan.Leonidas.plugin.Spartanizer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Anna Belozovsky
 * @since 22/04/2017
 */
public class Playground extends JFrame {
    private JPanel mainPanel;
    private JButton clearButton;
    private JButton spartanizeButton;
    private JTextArea inputArea;
    private JTextArea outputArea;

    public Playground() {
        super("Spartanizer Playground");
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        outputArea.setEditable(false);
        spartanizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spartanizeButtonClicked(e);
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButtonClicked(e);
            }
        });
    }

    private void spartanizeButtonClicked(ActionEvent e) {
        PsiFile file = PsiFileFactory.getInstance(Utils.getProject()).createFileFromText(JavaLanguage.INSTANCE, inputArea.getText());
        Spartanizer.spartanizeFileOnePass(file);
        outputArea.setText(file.getText());
//        outputArea.setText(inputArea.getText());
    }

    private void clearButtonClicked(ActionEvent e) {
        outputArea.setText("");
    }
}

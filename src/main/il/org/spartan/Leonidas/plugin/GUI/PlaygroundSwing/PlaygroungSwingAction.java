package il.org.spartan.Leonidas.plugin.GUI.PlaygroundSwing;

import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import il.org.spartan.Leonidas.auxilary_layer.Utils;

/**
 * @author Anna Belozovsky
 * @since 22/04/2017
 */
public class PlaygroungSwingAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        new Playground();
    }
}

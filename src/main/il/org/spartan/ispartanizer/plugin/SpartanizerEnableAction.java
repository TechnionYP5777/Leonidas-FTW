package il.org.spartan.ispartanizer.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.psi.PsiFile;

/**
 * Created by melanyc on 1/3/2017.
 */
public class SpartanizerEnableAction extends AnAction {
    @Override
    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        if (Toolbox.getInstance().checkExcluded(e.getData(LangDataKeys.PSI_FILE))) {
            presentation.setText("Enable Spartanization");
        } else {
            presentation.setText("Disable Spartanization");
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Toolbox tb = Toolbox.getInstance();
        if (tb.checkExcluded(psiFile)) {
            tb.includeFile(psiFile);
        } else {
            tb.excludeFile(psiFile);
        }
        System.out.println(psiFile.getName());
    }
}

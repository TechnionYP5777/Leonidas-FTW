package il.org.spartan.leonidas.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import il.org.spartan.leonidas.plugin.utils.logging.Logger;
import org.jetbrains.annotations.Nullable;

/**
 * @author Roey Maor
 * @author Oren Afek
 * @author Michal Cohen
 * @since 18-11-2016
 */
public class SpartanizerAction extends AnAction {

    private static Logger logger = new Logger(SpartanizerAction.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);

        if (psiClass != null) {
            logger.info("Spartanization action on\nPSI class '" + psiClass.getQualifiedName() + "'");
            Spartanizer.spartanizeFileOnePass(psiClass.getContainingFile());
        } else {
            logger.warn("Spartanization action retrieved null PSI class");
        }

    }

    /**
     * @param e the action event
     * @return the psiElement extracted from the event's context
     **/
    @Nullable
    private PsiElement getPsiElementFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        return psiFile == null || editor == null ? null : psiFile.findElementAt(editor.getCaretModel().getOffset());
    }

    /**
     * @param ¢ the action event
     * @return the psiClass extracted from the event's context
     **/
    @Nullable
    private PsiClass getPsiClassFromContext(AnActionEvent ¢) {
        return PsiTreeUtil.getParentOfType(getPsiElementFromContext(¢), PsiClass.class);
    }


}

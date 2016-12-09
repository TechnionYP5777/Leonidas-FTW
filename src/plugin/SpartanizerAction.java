package plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

/**
 * @author Roey Maor
 * @author Oren Afek
 * @author Michal Cohen
 * @since 2016.11.18
 */

public class SpartanizerAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiElement element = getPsiElementFromContext(e);
        PsiClass psiClass = getPsiClassFromContext(e);

        if (element == null || psiClass == null) {
            return;
        }

        Spartanizer.spartanizeCode(psiClass, element, getEventProject(e), psiClass.getContainingFile());

    }


    /**
     * @param e the action event
     * @return the psiElement extracted from the event's context
     **/

    @Nullable
    private PsiElement getPsiElementFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        int start = editor.getSelectionModel().getSelectionStart();
        int end = editor.getSelectionModel().getSelectionEnd();
        PsiElement psiElement = psiFile.findElementAt(start);

        if (psiFile == null || editor == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        return psiFile.findElementAt(offset);
    }

    /**
     * @param e the action event
     * @return the psiClass extracted from the event's context
     **/

    @Nullable
    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        return PsiTreeUtil.getParentOfType(getPsiElementFromContext(e), PsiClass.class);
    }


}

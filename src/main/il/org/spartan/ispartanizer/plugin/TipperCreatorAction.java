package il.org.spartan.ispartanizer.plugin;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

/**
 * @author Roey Maor
 * @author Oren Afek
 * @author Michal Cohen
 * @since 18-11-2016
 */
public class TipperCreatorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        PsiElement psiElement = e.getData(LangDataKeys.PSI_ELEMENT);
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final SelectionModel selectionModel = editor.getSelectionModel();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        PsiFile psiFile = psiClass.getContainingFile();
        PsiElement element = psiFile.findElementAt(start);
        PsiElement prev = element;
        PsiElement next = element.getParent();
        while (next != null && next.getText().startsWith(prev.getText())) {
            prev = next;
            next = next.getParent();
        }
        TipperCreator frame = new TipperCreator(prev);

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

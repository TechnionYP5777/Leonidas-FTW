import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;
import plugin.Toolbox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maorroey on 11/18/2016.
 */
public class Spartanizer extends AnAction {

    private static void spartanizeCode(PsiClass psiClass, PsiElement element) {
        Toolbox toolbox = Toolbox.getInstance();
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                toolbox.executeAllTippers(elementFactory, element);
            }
        }.execute();
    }

    /**
     * @param psiMethod a method with exactly two parameters
     * @param psiClass  the psiClass containing the psiMethod
     * @return the same method, with updated parameter names
     */
    private static void replaceWithThisIsSpartaMethod(PsiMethod psiMethod, PsiClass psiClass) {

        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());

        PsiParameter firstParam = psiMethod.getParameterList().getParameters()[0];
        PsiType firstParamType = firstParam.getType();
        PsiParameter secondParam = psiMethod.getParameterList().getParameters()[1];
        PsiType secondParamType = secondParam.getType();

        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                firstParam.replace(elementFactory.createParameter("this_is", firstParamType));
                secondParam.replace(elementFactory.createParameter("sparta", secondParamType));
            }

        }.execute();

    }

    public static void replaceWithThisIsSpartaMethod(PsiMethod psiMethod) {
        replaceWithThisIsSpartaMethod(psiMethod, psiMethod.getContainingClass());
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiElement element = getPsiElementFromContext(e);
        PsiClass psiClass = getPsiClassFromContext(e);
        if (element == null || psiClass == null) {
            return;
        }

        spartanizeCode(psiClass, element);

    }

    @Override
    public void update(AnActionEvent e) {
        //no need to update anything so far
    }

    /**
     * @param e the action event
     * @return the psiElement extracted from the event's context
     **/

    @Nullable
    private PsiElement getPsiElementFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
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

    /**
     * @param psiClass the psiClass to search on
     * @return a list of PSI methods that contain exactly 2 parameters
     */
    private List<PsiMethod> extractMethodsWith2Params(PsiClass psiClass) {
        return Arrays.stream(psiClass.getMethods()). //
                filter(m -> m.getParameterList().getParametersCount() == 2)
                .collect(Collectors.toList());
    }


}

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;

/**
 * Created by maorroey on 11/18/2016.
 */
public class ThisIsSparta extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        if (psiClass == null ){
            return;
        }
        ArrayList<PsiMethod> psiMethods = extractMethodsWith2Params(psiClass);


        for (PsiMethod method : psiMethods) {
            replaceWithThisIsSpartaMethod(method,psiClass);
        }
    }

    @Override
    public void update(AnActionEvent e) {
       //no need to update anything so far
    }

    /**
     *
     * @param e the action event
     * @return the psiClass extracted from the event's context
     */
    @Nullable
    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
        return psiClass;
    }

    /**
     *
     * @param psiClass the psiClass to search on
     * @return a list of PSI methods that contain exactly 2 parameters
     */
    private ArrayList<PsiMethod> extractMethodsWith2Params(PsiClass psiClass){
        ArrayList<PsiMethod> result = new ArrayList<PsiMethod>(0);
        for(PsiMethod psiMethod : psiClass.getMethods()) {
            if(psiMethod.getParameterList().getParametersCount() == 2){
                result.add(psiMethod);
            }
        }
        return result;
    }

    /**
     *
     * @param psiMethod a method with exactly two parameters
     * @param psiClass the psiClass containing the psiMethod
     * @return the same method, with updated parameter names
     */
    private void replaceWithThisIsSpartaMethod(PsiMethod psiMethod, PsiClass psiClass){

        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());

        PsiParameter firstParam = psiMethod.getParameterList().getParameters()[0];
        PsiType firstParamType = firstParam.getType();
        PsiParameter secondParam = psiMethod.getParameterList().getParameters()[1];
        PsiType secondParamType = secondParam.getType();

        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {

            @Override
            protected void run() throws Throwable {
                firstParam.replace( elementFactory.createParameter("this_is",firstParamType) );
                secondParam.replace( elementFactory.createParameter("sparta",secondParamType) );
            }

        }.execute();

    }




}

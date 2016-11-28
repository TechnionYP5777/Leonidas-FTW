package plugin;

import auxilary_layer.iz;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;


public class SimpleAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (!iz.isMethodDeclaration(element)) return;
        PsiParameterList paramList = ((PsiMethod) element).getParameterList();
        if (paramList.getParametersCount() != 2) return;
        TextRange range = new TextRange(element.getTextRange().getStartOffset(),
                element.getTextRange().getEndOffset());
        final PsiParameter firstParam = paramList.getParameters()[0];
        final PsiParameter secondParam = paramList.getParameters()[1];
        if ("this_is".equals(firstParam.getName()) && "sparta".equals(secondParam.getName())) return;

        holder.createErrorAnnotation(element, "Not spartanic enough!").registerFix(new IntentionAction() {
            @Nls
            @NotNull
            @Override
            public String getText() {
                return "Spartanize";
            }

            @Nls
            @NotNull
            @Override
            public String getFamilyName() {
                return "Spartinizer";
            }

            @Override
            public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
                return true;
            }

            @Override
            public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
                PsiType firstParamType = firstParam.getType();
                PsiType secondParamType = secondParam.getType();

                new WriteCommandAction.Simple(project, psiFile) {
                    @Override
                    protected void run() throws Throwable {
                        firstParam.replace(elementFactory.createParameter("this_is", firstParamType));
                        secondParam.replace(elementFactory.createParameter("sparta", secondParamType));
                    }
                }.execute();
            }

            @Override
            public boolean startInWriteAction() {
                return false;
            }
        });
    }
}

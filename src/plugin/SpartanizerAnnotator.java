package plugin;

import auxilary_layer.Utils;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.ui.JBColor;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;


public class SpartanizerAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {

        if (!Spartanizer.canTip(element)) {
            return;
        }

        Annotation annotation = holder.createInfoAnnotation(element, "Spartanize This!");
        annotation.registerFix(new IntentionAction() {
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
                return "plugin.SpartanizerAction";
            }

            @Override
            public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
                return true;
            }

            @Override
            public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
                Spartanizer.spartanizeCode(Utils.findClass(element), element, project, psiFile);
            }

            @Override
            public boolean startInWriteAction() {
                return false;
            }
        });

        TextAttributesKey.createTextAttributesKey("");
        TextAttributes textAttributes = new TextAttributes(null, null, JBColor.BLUE, EffectType.WAVE_UNDERSCORE, 0);
        annotation.setEnforcedTextAttributes(textAttributes);

    }
}

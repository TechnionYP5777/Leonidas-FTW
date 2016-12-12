import bridge.PsiAstMatching;
import bridge.TipperExecute;
import com.intellij.codeInsight.ExternalAnnotationsManager;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.annotation.Annotation;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.IncorrectOperationException;
import il.org.spartan.spartanizer.tipping.Tipper;
import org.apache.commons.lang.math.RandomUtils;
import org.eclipse.jdt.core.dom.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Map;
import java.util.Random;

/**
 * Created by roei on 12/9/16.
 */

public class d extends ExternalAnnotator<Map, Map> {
    @Nullable
    @Override
    public Map collectInformation(@NotNull PsiFile file) {
        return(new PsiAstMatching(file)).getMapping();
    }

    @Nullable
    @Override
    public Map collectInformation(@NotNull PsiFile file, @NotNull Editor editor, boolean hasErrors) {
        return collectInformation(file);
    }

    @Nullable
    @Override
    public Map doAnnotate(Map collectedInfo) {
        return collectedInfo;
    }

    @Override
    public void apply(@NotNull PsiFile file, Map annotationResult, @NotNull AnnotationHolder holder) {
        file.getNode().getPsi().accept(new PsiRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                final ASTNode astNode = (ASTNode) annotationResult.get(element);
                if (astNode == null) {
                    super.visitElement(element);
                    return;
                }

                Treasure treasure = (Treasure)ApplicationManager.getApplication().getComponent("spartanizer.Treasure");
                for(Tipper t : treasure.getAllTipers()) {
                    if(t.myActualOperandsClass() != astNode.getClass() && t.myActualOperandsClass() != null) continue;
                    if(!t.canTip(astNode) || t.cantTip(astNode)) continue;
                    Annotation annotation = holder.createInfoAnnotation(element, astNode.getClass().getSimpleName());
                    annotation.setEnforcedTextAttributes(
                            new TextAttributes(null, null, Color.RED, EffectType.BOXED, 0));
                    annotation.registerFix(new TIpIntention(t, element, astNode));
                    break;
                }
                super.visitElement(element);
            }
        });
        super.apply(file, annotationResult, holder);
    }
}

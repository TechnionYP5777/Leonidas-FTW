package plugin.intentions;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import il.org.spartan.spartanizer.engine.Tip;
import il.org.spartan.spartanizer.tipping.Tipper;
import il.org.spartan.spartanizer.tipping.TipperFailure;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by roei on 12/9/16.
 *
 * responsible for tips being shown in the intentions menu (when alt+enter is clicked)
 */
public class TIpIntention implements IntentionAction {
    Tipper tipper;
    PsiElement psiElement;
    ASTNode astNode;

    public TIpIntention(Tipper tipper, PsiElement psiElement, ASTNode astNode) {
        this.tipper = tipper;
        this.psiElement = psiElement;
        this.astNode = astNode;
    }

    @Nls
    @NotNull
    @Override
    public String getText() {
        return tipper.description(astNode);
    }

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return "" + astNode.getLength();
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
        Document jdtDocument = new Document(psiFile.getText());
        Tip tip = null;
        try {
            tip = tipper.tip(astNode);
        } catch (TipperFailure tipperFailure) {
            tipperFailure.printStackTrace();
        }
        ASTRewrite r = ASTRewrite.create(astNode.getAST());
        tip.go(r, null);
        TextEdit textEdit = r.rewriteAST(jdtDocument, null);
        try {
            textEdit.apply(jdtDocument);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        final String replacement = jdtDocument.get();
        new WriteCommandAction.Simple(project, psiFile) {
            @Override
            protected void run() throws Throwable {
                PsiDocumentManager.getInstance(project).getDocument(psiFile).setText(replacement);
            }
        }.execute();

    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}

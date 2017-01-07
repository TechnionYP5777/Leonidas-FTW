package il.org.spartan.ispartanizer.plugin;

import com.intellij.codeInsight.daemon.impl.DefaultHighlightVisitorBasedInspection;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

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
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        psiFile.getVirtualFile().refresh(true, true);
        File f = new File(psiFile.getVirtualFile().getPath());
        try {
            Files.setLastModifiedTime(f.toPath(), FileTime.from(Instant.now()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        DefaultHighlightVisitorBasedInspection.runGeneralHighlighting(psiFile, false, true, true);

    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Project p = psiFile.getProject();
        Toolbox tb = Toolbox.getInstance();
        if (tb.checkExcluded(psiFile)) {
            tb.includeFile(psiFile);
        } else {
            tb.excludeFile(psiFile);
        }

        psiFile.getVirtualFile().refresh(true, true);
        /*try {
            psiFile.getVirtualFile().setWritable(true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }*/
        File f = new File(psiFile.getVirtualFile().getPath());
        //f.setWritable(true);
        try {
            Files.setLastModifiedTime(f.toPath(), FileTime.from(Instant.now()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        DefaultHighlightVisitorBasedInspection.runGeneralHighlighting(psiFile, false, true, true);
        SpartanizerAnnotator x = new SpartanizerAnnotator();
        psiFile.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                x.annotate(element);
            }
        });
        List<CharSequence> bla = new LinkedList<>();
        bla.add(";");
        try {
            List<String> l = Files.readAllLines(f.toPath());
            l.add(";");
            Files.write(f.toPath(), l);
            l.remove(l.size() - 1);
            Files.write(f.toPath(), l);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //Files.

        //SpartanizerAnnotator a = new SpartanizerAnnotator();
        /*psiFile.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                a.annotate(element, );
            }
        });*/
        //(new TextEditorHighlightingPass(p, PsiDocumentManager.getInstance(p).getDocument(psiFile))).
        //PsiModificationTracker
        //new TextEditorHighlightingPass(p, PsiDocumentManager.getInstance(p).getDocument(psiFile)))
        System.out.println(psiFile.getName());
    }
}

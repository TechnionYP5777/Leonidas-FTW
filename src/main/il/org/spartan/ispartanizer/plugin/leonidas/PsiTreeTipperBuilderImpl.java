package il.org.spartan.ispartanizer.plugin.leonidas;

import com.google.common.io.Files;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Oren Afek
 * @since 06/01/17
 */
public class PsiTreeTipperBuilderImpl implements PsiTreeTipperBuilder {

    private static final String FILE_NAME = "RemoveCurlyBracesFromIfStatement" + ".java";
    private static final String FILE_PATH = "/spartanizer/LeonidasTippers/";

    public PsiTreeTipperBuilderImpl buildTipperPsiTree(String fileName, Project project) {
        return this;
    }

    public PsiElement getPsiTree(){
        return null;
    }

    public Map<PsiElement, ElementDescriptor> getTreeDescriptor(){
        return null;
    }

    PsiElement getPsiTreeFromFile(String fileName, Project project) throws IOException {
        File file = new File(this.getClass().getResource(FILE_PATH + fileName).getPath());
        PsiFile psiFile = PsiFileFactory.getInstance(project).createFileFromText(fileName,
                FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName()),
                String.join("\n", Files.readLines(file, StandardCharsets.UTF_8)));
        return null;
    }

    PsiElement getPsiElement(String fileName) {
        return null;
    }
}

package auxilary_layer;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * Created by amirsagiv on 12/8/16.
 */
public class PsiStringConverter {

    public static PsiElement convertStringToPsi(PsiRewrite rewrite, String str){
        PsiFile file = rewrite.getFileFactory().createFileFromText("tempFile", JavaFileType.INSTANCE,str);
        return file.getNavigationElement();
    }

    public static String convertPsiToString(PsiElement element){
        return element.getText();
    }
}

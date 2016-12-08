package auxilary_layer;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * Created by amirsagiv on 12/8/16.
 */
public class PsiStringConverter {

    public static PsiElement CreatePsiFromString(PsiRewrite rewrite, String str){
        if(rewrite == null || str == null)
            return null;

        PsiFile file = rewrite.getFileFactory().createFileFromText("tempFile", JavaFileType.INSTANCE,str);
        return file.getNavigationElement();
    }

    public static String createStringFromPsi(PsiElement element){
        return element.getText();
    }
}

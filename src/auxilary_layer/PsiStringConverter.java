package auxilary_layer;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.PsiElement;

/**
 * @Author Amir Sagiv
 * @since 8/12/2016.
 */

public class PsiStringConverter {

    public static PsiElement convertStringToPsi(PsiRewrite r, String s) {
        return r.getFileFactory().createFileFromText("tempFile", JavaFileType.INSTANCE, s).getNavigationElement();
    }

    public static String convertPsiToString(PsiElement ¢) {
        return ¢.getText();
    }
}

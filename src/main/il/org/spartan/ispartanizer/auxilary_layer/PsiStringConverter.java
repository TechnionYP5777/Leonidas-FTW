package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.PsiElement;

/**
 * @Author Amir Sagiv
 * @since 8/12/2016.
 */

public class PsiStringConverter {

    public static PsiElement convertStringToPsi(PsiRewrite r, String s) {
        //TODO: as we discussed the layer of string to PSI needs to be fixed
        //return r.getFileFactory().createFileFromText("tempFile", JavaFileType.INSTANCE, s).getNavigationElement();
        return null;
    }

    public static String convertPsiToString(PsiElement ¢) {
        return ¢.getText();
    }
}

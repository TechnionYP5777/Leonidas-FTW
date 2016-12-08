package auxilary_layer;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * Created by amirsagiv on 12/8/16.
 */

/**
 * This is a util class that should be used as a converter between a String to a Psi tree and backwards.
 *
 */
public class PsiStringCreator {

    /**
     *
     * @param rewrite - a PsiRewrite object used to get the PsiFileFactory from.
     * @param str -The string requested to convert
     * @return - a PsiElement representing the string given to the method.
     */
    public static PsiElement createPsiFromString(PsiRewrite rewrite, String str){
        if(str == null || rewrite == null)
            return null;

        PsiFile file = rewrite.getFileFactory().createFileFromText("tempFile", JavaFileType.INSTANCE,str);
        PsiElement requestedElement = file.getNavigationElement();
        return requestedElement;
    }

    /**
     *
     * @param element - The PsiElement requested to convert.
     * @return a String representing the PsiElement given to the method.
     */
    public static String createStringFromPsi(PsiElement element){
        if(element == null)
            return null;
        return element.getText();
    }
}

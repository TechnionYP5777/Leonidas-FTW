package auxilary_layer;

import com.intellij.openapi.editor.Document;
import com.intellij.psi.*;

/**
 * Created by maorroey on 12/9/2016.
 */
public class PsiUtils {
    public static Document getDocumentFromPsiElement(PsiElement ele){
        PsiFile associatedFile = ele.getContainingFile();
        return PsiDocumentManager.getInstance( associatedFile.getProject()).getDocument(associatedFile);
    }
}

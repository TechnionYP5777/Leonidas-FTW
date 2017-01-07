package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.iz;

import java.util.Map;

//import static il.org.spartan.ispartanizer.plugin.leonidas.PsiDescriptionParameters.*;
import com.intellij.openapi.util.Key;

/**
 * @author AnnaBel7 and michal cohen
 * @since 06/01/2017.
 */
public class PsiTreeMatcher {

    //TODO: add a way to represent comparison of amount

    public static boolean match(PsiElement treeTemplate, PsiElement treeToMatch) {
        if (!iz.theSameType(treeToMatch, treeTemplate)) {
            return false;
        }
        if (iz.block(treeToMatch)) {
            if (az.block(treeToMatch).getStatements().length != treeTemplate.getUserData(KeyDescriptionParameters.NO_OF_STATEMENTS)) {
                return false;
            } else {
                //TODO: implement cases of comparison of amount
            }
        }

        boolean res = true;
        for (PsiElement treeTemplateChild = treeTemplate.getFirstChild(), treeToMatchChild = treeToMatch.getFirstChild(); treeTemplateChild != null && treeToMatchChild != null; treeTemplateChild = treeTemplateChild.getNextSibling(), treeToMatchChild = treeToMatchChild.getNextSibling()) {
            res = res && match(treeTemplateChild, treeToMatchChild);
        }
        return res;
    }

}

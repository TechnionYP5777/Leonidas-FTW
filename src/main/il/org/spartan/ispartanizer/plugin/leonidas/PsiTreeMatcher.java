package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.auxilary_layer.step;

//import static il.org.spartan.ispartanizer.plugin.leonidas.PsiDescriptionParameters.*;

/**
 * @author AnnaBel7 and michal cohen
 * @since 06/01/2017.
 */
public class PsiTreeMatcher {

    public static boolean match(PsiElement treeTemplate, PsiElement treeToMatch) {
        if (!iz.theSameType(treeToMatch, treeTemplate)) {
            return false;
        }
        if (iz.block(treeToMatch)) {
            if (treeTemplate.getUserData(KeyDescriptionParameters.NO_OF_STATEMENTS).notConforms(az.block(treeToMatch).getStatements().length)) {
                return false;
            }
        }
        boolean res = true;
        for (PsiElement treeTemplateChild = treeTemplate.getFirstChild(), treeToMatchChild = treeToMatch.getFirstChild(); treeTemplateChild != null && treeToMatchChild != null; treeTemplateChild = step.nextSibling(treeTemplateChild), treeToMatchChild = step.nextSibling(treeToMatchChild)) {
            res = res && match(treeTemplateChild, treeToMatchChild);
        }
        return res;
    }

}

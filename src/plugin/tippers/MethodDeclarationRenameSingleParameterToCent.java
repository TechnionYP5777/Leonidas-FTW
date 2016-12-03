package plugin.tippers;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;
import auxilary_layer.PsiRewrite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Cohen
 * @since 2016.12.1
 */

public class MethodDeclarationRenameSingleParameterToCent implements Tipper<PsiMethod> {

    // TODO michal: convert to iz
    @Override
    public boolean canTip(PsiMethod m) {
        return !m.isConstructor() && m.getModifierList().hasExplicitModifier("abstrat") && m.getParameterList().getParametersCount() == 1 && isSpecialChar(m.getParameterList().getParameters()[0].getName());
    }

    // TODO michal: convert to in
    private boolean isSpecialChar(String s) {
        List<String> l = new ArrayList<String>();
        l.add("$");
        l.add("¢");
        l.add("_");
        l.add("__");
        return l.contains(s);
    }

    @Override
    public String description(PsiMethod m) {
        return m.getName();
    }

    @Override
    public Tip tip(PsiMethod m) {
        if (m == null || !canTip(m)) {
            return null;
        }
        // TODO michal: check if there was a variable named cent
        return new Tip() {
            public void go(PsiRewrite r) {
                r.replace( m.getParameterList().getParameters()[0].getNameIdentifier(), r.factory.createIdentifier("¢") );
            }
        };

    }
}

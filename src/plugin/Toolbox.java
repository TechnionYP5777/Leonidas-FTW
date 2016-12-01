package plugin;

import com.intellij.psi.PsiElement;
import plugin.tippers.EnhancedForRedundantContinue;
import plugin.tippers.InfixFactorNegatives;
import plugin.tippers.LambdaExpressionRemoveRedundantCurlyBraces;
import plugin.tippers.MethodDeclarationRenameSingleParameterToCent;
import plugin.tipping.Tipper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oren Afek
 * @author Michal Cohen
 * @since 2016.12.1
 */
public class Toolbox {

    private Map<Class<? extends PsiElement>, Tipper> tipperMap;

    private Toolbox() {
        this.tipperMap = new HashMap<>();
    }

    private Toolbox add(Tipper<? extends PsiElement> tipper) {
        this.tipperMap.put(tipper.getPsiClass(), tipper);
        return this;
    }

    public Toolbox getToolbox() {
        return this //
                .add(new EnhancedForRedundantContinue()) //
                .add(new InfixFactorNegatives()) //
                .add(new LambdaExpressionRemoveRedundantCurlyBraces()) //
                .add(new MethodDeclarationRenameSingleParameterToCent());
    }

    public Toolbox getEmptyToolbox() {
        return new Toolbox();
    }


}

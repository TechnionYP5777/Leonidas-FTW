package plugin;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
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
public enum Toolbox {
    INSTANCE;

    final private Map<Class<? extends PsiElement>, Tipper> tipperMap;

    Toolbox() {
        this.tipperMap = new HashMap<>();
    }

    public static Toolbox getInstance() {
        return INSTANCE //
                //.add(new EnhancedForRedundantContinue()) //
                .add(new LambdaExpressionRemoveRedundantCurlyBraces()) //
                .add(new MethodDeclarationRenameSingleParameterToCent());
    }

    private Toolbox add(Tipper<? extends PsiElement> tipper) {
        this.tipperMap.put(tipper.getPsiClass(), tipper);
        return this;
    }

    public Toolbox getEmptyToolbox() {
        this.tipperMap.clear();
        return this;
    }

    public Toolbox executeAllTippers(PsiElementFactory elementFactory, PsiElement element) {
        tipperMap.values().stream() //
                .filter(tipper -> tipper.canTip(element)) //
                .forEach(tipper -> tipper.tip(element));

        return this;
    }


}

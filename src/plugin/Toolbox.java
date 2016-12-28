package plugin;

import auxilary_layer.PsiRewrite;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
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
                .add(new MethodDeclarationRenameSingleParameterToCent())//
                ;
        //.add(new DefaultsTo());
                //.add(new SafeReference());
    }

    private Toolbox add(Tipper<? extends PsiElement> tipper) {
        this.tipperMap.put(tipper.getPsiClass(), tipper);
        return this;
    }

    public Toolbox getEmptyToolbox() {
        this.tipperMap.clear();
        return this;
    }

    public Toolbox executeAllTippers(PsiElement element, Project project, PsiFile psiFile) {
        tipperMap.values().stream() //
                .filter(tipper -> tipper.canTip(element)) //
                .forEach(tipper -> tipper.tip(element).go(new PsiRewrite().psiFile(psiFile).project(project)));
        return this;
    }

    /**
     * Can element by spartanized
     *
     * @param element JD
     * @return true iff there exists a tip that tip.canTip(element) is true
     */
    public boolean canTip(PsiElement element) {
        return tipperMap.values().stream().anyMatch(tip -> tip.canTip(element));
    }


}

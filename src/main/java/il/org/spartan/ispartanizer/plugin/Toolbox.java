package il.org.spartan.ispartanizer.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;
import il.org.spartan.ispartanizer.auxilary_layer.type;
import il.org.spartan.ispartanizer.plugin.tippers.*;
import il.org.spartan.ispartanizer.plugin.tipping.Tipper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Oren Afek
 * @author Michal Cohen
 * @since 2016.12.1
 */
public enum Toolbox {
    INSTANCE;

    final private Map<Class<? extends PsiElement>, List<Tipper>> tipperMap;

    Toolbox() {
        this.tipperMap = new HashMap<>();
    }

    public static Toolbox getInstance() {
        return INSTANCE //
                //.add(new EnhancedForRedundantContinue()) //
                .add(new Unless())
                .add(new LambdaExpressionRemoveRedundantCurlyBraces()) //
                .add(new LispLastElement())
                .add(new MethodDeclarationRenameSingleParameterToCent())//
                .add(new AnyMatch())
                //.add(new DefaultsTo());
                .add(new SafeReference());
    }

    private Toolbox add(Tipper<? extends PsiElement> tipper) {
        tipperMap.putIfAbsent(tipper.getPsiClass(), new LinkedList<>());
        tipperMap.get(tipper.getPsiClass()).add(tipper);
        return this;
    }

    public Toolbox getEmptyToolbox() {
        this.tipperMap.clear();
        return this;
    }

    public Toolbox executeAllTippers(PsiElement element, Project project, PsiFile psiFile) {

        tipperMap.get(type.of(element)).stream() //
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
        return tipperMap.get(type.of(element)).stream().anyMatch(tip -> tip.canTip(element));
    }


}

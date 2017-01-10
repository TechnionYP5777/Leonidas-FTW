package il.org.spartan.ispartanizer.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;
import il.org.spartan.ispartanizer.auxilary_layer.type;
import il.org.spartan.ispartanizer.plugin.tippers.*;
import il.org.spartan.ispartanizer.plugin.tipping.Tipper;

import java.io.File;
import java.util.*;

/**
 * @author Oren Afek
 * @author Michal Cohen
 * @since 2016.12.1
 */
public enum Toolbox {
    INSTANCE;

    static boolean wasInitialize = false;
    final private Map<Class<? extends PsiElement>, List<Tipper>> tipperMap;
    Set<PsiFile> excludedFiles;

    Toolbox() {
        this.tipperMap = new HashMap<>();
        excludedFiles = new HashSet<>();
    }

    public static Toolbox getInstance() {
        if (!wasInitialize) {
            initializeInstance();
        }
        return INSTANCE;
    }

    private static void initializeInstance() {
        wasInitialize = true;
        INSTANCE //
                .add(new SafeReference())
                .add(new Unless())
                .add(new LambdaExpressionRemoveRedundantCurlyBraces()) //
                .add(new LispLastElement())
                .add(new DefaultsTo())
                .add(new MethodDeclarationRenameSingleParameterToCent())//
                .add(new AnyMatch())
                .add(new Delegator());
        createLeonidasTipperBuilders();
    }

    private static void createLeonidasTipperBuilders() {
        List<File> tippers = Arrays.asList(new File(Toolbox.class.getResource("/spartanizer/LeonidasTippers").getPath()).listFiles());
        tippers.forEach(f -> INSTANCE.add(new LeonidasTipper(f)));
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

        if (checkExcluded(element.getContainingFile())) {
            return this;
        }
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
        return (!checkExcluded(element.getContainingFile()) && canTipType(type.of(element))) && tipperMap.get(type.of(element)).stream().anyMatch(tip -> tip.canTip(element));
    }

    public boolean checkExcluded(PsiFile f) {
        return f == null || excludedFiles.contains(f);
    }

    public void excludeFile(PsiFile f) {
        excludedFiles.add(f);
    }

    public void includeFile(PsiFile f) {
        excludedFiles.remove(f);
    }

    private boolean canTipType(Class<? extends PsiElement> t) {
        return tipperMap.keySet().stream().anyMatch(x -> x.equals(t));
    }
}

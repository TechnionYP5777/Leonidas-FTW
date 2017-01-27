package il.org.spartan.ispartanizer.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;
import il.org.spartan.ispartanizer.auxilary_layer.Utils;
import il.org.spartan.ispartanizer.auxilary_layer.type;
import il.org.spartan.ispartanizer.plugin.tippers.*;
import il.org.spartan.ispartanizer.plugin.tipping.Tipper;

import java.io.File;
import java.util.*;

/**
 * @author Oren Afek
 * @author Michal Cohen
 * @since 01-12-2016
 */
public enum Toolbox {
    INSTANCE;

    static boolean wasInitialize;
    private final Map<Class<? extends PsiElement>, List<Tipper>> tipperMap;
    Set<VirtualFile> excludedFiles;

    Toolbox() {
        this.tipperMap = new HashMap<>();
        excludedFiles = new HashSet<>();
    }

    public static Toolbox getInstance() {
        if (!wasInitialize)
            initializeInstance();
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
                .add(new Delegator());
        createLeonidasTipperBuilders();
    }

    private static void createLeonidasTipperBuilders() {
        Arrays.asList(new File(
                Utils.fixSpacesProblemOnPath(Toolbox.class.getResource("/spartanizer/LeonidasTippers").getPath()))
                .listFiles())
                .forEach(f -> INSTANCE.add(new LeonidasTipper(f)));
    }

    private Toolbox add(Tipper<? extends PsiElement> ¢) {
        tipperMap.putIfAbsent(¢.getPsiClass(), new LinkedList<>());
        tipperMap.get(¢.getPsiClass()).add(¢);
        return this;
    }

    public Toolbox getEmptyToolbox() {
        this.tipperMap.clear();
        return this;
    }

    public Toolbox executeAllTippers(PsiElement e, Project p, PsiFile f) {

        if (checkExcluded(e.getContainingFile()))
            return this;
        tipperMap.get(type.of(e)).stream() //
                .filter(tipper -> tipper.canTip(e)) //
                .findFirst()
                .get().tip(e).go(new PsiRewrite().psiFile(f).project(p));
        return this;
    }

    /**
     * Can element by spartanized
     *
     * @param e JD
     * @return true iff there exists a tip that tip.canTip(element) is true
     */
    public boolean canTip(PsiElement e) {
        return (!checkExcluded(e.getContainingFile()) && canTipType(type.of(e))) && tipperMap.get(type.of(e)).stream().anyMatch(tip -> tip.canTip(e));
    }

    public <T extends PsiElement> Tipper<T> getTipper(PsiElement e) {
        try {
            if (!checkExcluded(e.getContainingFile()) && canTipType(type.of(e)) &&
                    tipperMap.get(type.of(e)).stream().anyMatch(tip -> tip.canTip(e)))
                return tipperMap.get(type.of(e)).stream().filter(tip -> tip.canTip(e)).findFirst().get();
        } catch (Exception ignore) {
        }
        return new NoTip<>();
    }

    public boolean checkExcluded(PsiFile ¢) {
        return ¢ == null || excludedFiles.contains(¢.getVirtualFile());
    }

    public void excludeFile(PsiFile ¢) {
        excludedFiles.add(¢.getVirtualFile());
    }

    public void includeFile(PsiFile ¢) {
        excludedFiles.remove(¢.getVirtualFile());
    }

    private boolean canTipType(Class<? extends PsiElement> e) {
        return tipperMap.keySet().stream().anyMatch(x -> x.equals(e));
    }
}

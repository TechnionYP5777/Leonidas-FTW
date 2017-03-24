package il.org.spartan.ispartanizer.plugin;

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

    static boolean wasInitialize = false;
    final private Map<Class<? extends PsiElement>, List<Tipper>> tipperMap = new HashMap<>();
    Set<VirtualFile> excludedFiles = new HashSet<>();
    Set<Class<? extends PsiElement>> operableTypes = new HashSet<>();
    boolean tmp = false;

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
                .add(new Delegator());
        createLeonidasTipperBuilders();
    }

    private static void createLeonidasTipperBuilders() {
        List<File> tippers = Arrays.asList(new File(Utils.fixSpacesProblemOnPath(Toolbox.class
                .getResource("/spartanizer/LeonidasTippers").getPath())).listFiles());
        tippers.forEach(f -> INSTANCE.add(new LeonidasTipper(f)));
    }

    private Toolbox add(Tipper<? extends PsiElement> tipper) {
        tipperMap.putIfAbsent(tipper.getPsiClass(), new LinkedList<>());
        operableTypes.add(tipper.getPsiClass());
        tipperMap.get(tipper.getPsiClass()).add(tipper);
        return this;
    }

    public boolean isElementOfOperableType(PsiElement element) {
        return operableTypes.stream().anyMatch(t -> t.isAssignableFrom(element.getClass()));
    }

    public Toolbox executeAllTippers(PsiElement element) {

        if (checkExcluded(element.getContainingFile())) {
            return this;
        }
        if (!isElementOfOperableType(element)) {
            return this;
        }
        tipperMap.get(type.of(element)).stream() //
                .filter(tipper -> tipper.canTip(element)) //
                .findFirst()
                .ifPresent(t -> t.tip(element).go(new PsiRewrite().psiFile(element.getContainingFile()).project(element.getProject())));
        return this;
    }

    /**
     * Can element by spartanized
     *
     * @param element JD
     * @return true iff there exists a tip that tip.canTip(element) is true
     */
    public boolean canTip(PsiElement element) {
        if (!checkExcluded(element.getContainingFile()) && canTipType(type.of(element)) && tipperMap.get(type.of(element)).stream().anyMatch(tip -> tip.canTip(element))) {
            if (!tmp) {
                //JOptionPane.showMessageDialog(null, "" + type.of(element).getName() +" \n\n"+ tipperMap.get(type.of(element)).stream().map(t -> t.description()).reduce((s1, s2) -> s1 + "\n" + s2), "toolbox", JOptionPane.WARNING_MESSAGE);
                tmp = !tmp;
            }
            return true;
        }
        return false;
    }

    public <T extends PsiElement> Tipper<T> getTipper(PsiElement element) {
        try {
            if (!checkExcluded(element.getContainingFile()) && canTipType(type.of(element)) &&
                    tipperMap.get(type.of(element)).stream().anyMatch(tip -> tip.canTip(element))) {
                return tipperMap.get(type.of(element)).stream().filter(tip -> tip.canTip(element)).findFirst().get();
            }
        } catch (Exception ignore) {
        }
        return new NoTip<>();
    }

    public boolean checkExcluded(PsiFile f) {
        return f == null || excludedFiles.contains(f.getVirtualFile());
    }

    public void excludeFile(PsiFile f) {
        excludedFiles.add(f.getVirtualFile());
    }

    public void includeFile(PsiFile f) {
        excludedFiles.remove(f.getVirtualFile());
    }

    private boolean canTipType(Class<? extends PsiElement> t) {
        return tipperMap.keySet().stream().anyMatch(x -> x.equals(t));
    }
}

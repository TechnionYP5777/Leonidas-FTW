package il.org.spartan.Leonidas.plugin;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import il.org.spartan.Leonidas.auxilary_layer.PsiRewrite;
import il.org.spartan.Leonidas.auxilary_layer.Utils;
import il.org.spartan.Leonidas.auxilary_layer.type;
import il.org.spartan.Leonidas.plugin.tippers.*;
import il.org.spartan.Leonidas.plugin.tippers.leonidas.LeonidasTipperDefinition;
import il.org.spartan.Leonidas.plugin.tipping.Tipper;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Oren Afek
 * @author Michal Cohen
 * @since 01-12-2016
 */
public enum Toolbox {
    INSTANCE;
    static boolean wasInitialize;
    private final Map<Class<? extends PsiElement>, List<Tipper>> tipperMap = new HashMap<>();
    public boolean playground = false;
    Set<VirtualFile> excludedFiles = new HashSet<>();
    Set<Class<? extends PsiElement>> operableTypes = new HashSet<>();
    boolean tmp;

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
        createLeonidasTipperBuilders2();
    }

    @SuppressWarnings("ConstantConditions")
    private static void createLeonidasTipperBuilders() {
        Arrays.asList(new File(
                Utils.fixSpacesProblemOnPath(Toolbox.class.getResource("/spartanizer/LeonidasTippers").getPath()))
                .listFiles())
                .forEach(f -> INSTANCE.add(new LeonidasTipper(f)));
    }

    //TODO get Leonidas resources
    private static void createLeonidasTipperBuilders2() {
        String x = "";
        x = Utils.pathToClass(LeonidasTipperDefinition.class);
        (new Reflections(LeonidasTipperDefinition.class)).getSubTypesOf(LeonidasTipperDefinition.class).stream()
                .forEach(c -> {
                    try {
                        INSTANCE.add(new LeonidasTipper2(c.getSimpleName(), Utils.getSourceCode(c)));
                    } catch (IOException e) {
                        System.out.print("failed to read file: " + c.getName());
                        e.printStackTrace();
                    }
                });
    }

    //stub method
    public static List<LeonidasTipper> getAllTippers() {
        return new ArrayList<>();
    }

    private Toolbox add(Tipper<? extends PsiElement> t) {
        tipperMap.putIfAbsent(t.getPsiClass(), new LinkedList<>());
        operableTypes.add(t.getPsiClass());
        tipperMap.get(t.getPsiClass()).add(t);
        return this;
    }

    public boolean isElementOfOperableType(PsiElement e) {
        return operableTypes.stream().anyMatch(t -> t.isAssignableFrom(e.getClass()));
    }

    public Toolbox executeAllTippers(PsiElement e) {
        if (checkExcluded(e.getContainingFile()) || !isElementOfOperableType(e))
            return this;
        tipperMap.get(type.of(e)).stream().filter(tipper -> tipper.canTip(e)).findFirst()
                .ifPresent(t -> t.tip(e).go(new PsiRewrite().psiFile(e.getContainingFile()).project(e.getProject())));
        return this;
    }

    /**
     * Can element by spartanized
     *
     * @param e JD
     * @return true iff there exists a tip that tip.canTip(element) is true
     */
    public boolean canTip(PsiElement e) {
        return (!checkExcluded(e.getContainingFile()) && canTipType(type.of(e)) && tipperMap.get(type.of(e)).stream().anyMatch(tip -> tip.canTip(e)));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Tipper getTipper(PsiElement e) {
        try {
            if (!checkExcluded(e.getContainingFile()) && canTipType(type.of(e)) &&
                    tipperMap.get(type.of(e)).stream().anyMatch(tip -> tip.canTip(e)))
                return tipperMap.get(type.of(e)).stream().filter(tip -> tip.canTip(e)).findFirst().get();
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

    private boolean canTipType(Class<? extends PsiElement> c) {
        return tipperMap.keySet().stream().anyMatch(x -> x.equals(c));
    }
}

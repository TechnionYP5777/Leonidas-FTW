package il.org.spartan.ispartanizer.plugin.tippers;

import com.google.common.io.Files;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;
import il.org.spartan.ispartanizer.auxilary_layer.Utils;
import il.org.spartan.ispartanizer.plugin.Toolbox;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;
import il.org.spartan.ispartanizer.plugin.tipping.Tipper;
import il.org.spartan.ispartanizer.plugin.tipping.TipperCategory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by maorroey on 12/26/2016.
 */
public abstract class NanoPatternTipper<N extends PsiElement> implements Tipper<N>, TipperCategory.Nanos {
    protected static <N extends PsiElement> boolean anyTips(final Collection<Tipper<N>> ns, final N n) {
        return n != null && ns.stream().anyMatch(t -> t.canTip(n));
    }

    protected static <N extends PsiElement> Tipper<N> firstTipperThatCanTip(final Collection<Tipper<N>> ns, final N n) {
        return ns.stream().filter(t -> t.canTip(n)).findFirst().get();
    }

    public static <N extends PsiElement> Tip firstTip(final Collection<Tipper<N>> ns, final N n) {
        return firstTipperThatCanTip(ns, n).tip(n);
    }

    String className() {
        return this.getClass().getSimpleName();
    }

    /**
     * @param e the PsiElement on which the tip will be applied
     * @return an element tip to apply on e.
     */
    public Tip tip(final N e) {
        if (!canTip(e)) return null;
        return new Tip(description(e), e, this.getClass()) {
            @Override
            public void go(PsiRewrite r) {
                PsiElement e_tag = createReplacement(e);
                new WriteCommandAction.Simple(e.getProject(), e.getContainingFile()) {
                    @Override
                    protected void run() throws Throwable {
                        createEnvironment(e);
                        e.replace(e_tag);
                    }
                }.execute();
            }
        };
    }

    /**
     * This method should be override in order to create the psi element that will
     * replace e.
     *
     * @param e - the element to be replaced
     * @return the PsiElement that will replace e.
     */
    protected abstract PsiElement createReplacement(final N e);

    /**
     * @param e the PsiElement that the tip is applied to
     * @return the PsiFile in which e is contained
     * @throws IOException if for some reason writing to the users disk throws exception.
     */
    private PsiFile insertSpartanizerUtils(PsiElement e) throws IOException {
        PsiFile pf;
        PsiDirectory srcDir = e.getContainingFile().getContainingDirectory();
        // creates the directory and adds the file if needed
        try {
            srcDir.checkCreateSubdirectory("spartanizer");
            PsiDirectory pd = e.getContainingFile().getContainingDirectory().createSubdirectory("spartanizer");
            URL is = this.getClass().getResource("/spartanizer/SpartanizerUtils.java");
            File file = new File(is.getPath());
            FileType type = FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName());
            List<String> ls = Files.readLines(file, StandardCharsets.UTF_8);
            pf = PsiFileFactory.getInstance(e.getProject()).createFileFromText("SpartanizerUtils.java", type, String.join("\n", ls));
            pd.add(pf);
            Toolbox.getInstance().excludeFile(pf);
        } catch (IncorrectOperationException x) {
            PsiDirectory pd = srcDir.getSubdirectories()[0];
            pf = pd.getFiles()[0];
        }
        return pf;
    }

    /**
     * Inserts "import static spartanizer/SpartanizerUtils/*;" to the users code.
     *
     * @param e  - the PsiElement on which the tip is applied.
     * @param pf - the psi file in which e is contained.
     */
    private void insertImportStatement(PsiElement e, PsiFile pf) {
        PsiImportStaticStatement piss = JavaPsiFacade.getElementFactory(e.getProject()).createImportStaticStatement(PsiTreeUtil.getChildOfType(pf, PsiClass.class), "*");
        PsiImportList pil = Utils.getImportList(e.getContainingFile());
        if (!Arrays.stream(pil.getImportStaticStatements()).anyMatch(x -> x.getText().contains("spartanizer"))) {
            pil.add(piss);
        }

    }

    /**
     * Inserts import statement and copies file in order to make the nano patterns compile
     *
     * @param e - the PsiElement on which the tip is applied.
     * @throws IOException - if for some reason writing new file to the users disk throws exception.
     */
    private void createEnvironment(final N e) throws IOException {
        PsiFile pf = insertSpartanizerUtils(e);
        insertImportStatement(e, pf);
    }

    protected abstract Tip pattern(final N ¢);
}

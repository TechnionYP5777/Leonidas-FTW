package plugin.tippers;

import auxilary_layer.*;
import com.google.common.io.Files;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class Unless extends NanoPatternTipper<PsiConditionalExpression> {

    @Override
    public boolean canTip(PsiElement e) {
        return (iz.conditionalExpression(e) && (iz.nullExpression(az.conditionalExpression(e).getThenExpression())));
    }

    @Override
    public String description() {
        return "Change conditional expression to fluent Unless";
    }

    @Override
    public String description(PsiConditionalExpression psiConditionalExpression) {
        return "Change conditional expression: " + psiConditionalExpression.getText() + " to fluent Unless";
    }

    @Override
    public Tip tip(PsiConditionalExpression e) {
        if (!canTip(e)) return null;
        return new Tip(description(e), e, this.getClass()) {
            @Override
            public void go(PsiRewrite r) {

                PsiExpression e_tag = JavaPsiFacade.getElementFactory(e.getProject()).createExpressionFromText("unless(" + e.getCondition().getText() + ", " + e.getElseExpression().getText() + ")", e);

                if (iz.nullExpression(az.conditionalExpression(e).getThenExpression())) {

                    new WriteCommandAction.Simple(e.getProject(), e.getContainingFile()) {
                        @Override
                        protected void run() throws Throwable {
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
                            } catch (IncorrectOperationException e) {
                                PsiDirectory pd = srcDir.getSubdirectories()[0];
                                pf = pd.getFiles()[0];
                            }
                            // adds the import statement if needed
                            PsiImportStaticStatement piss = JavaPsiFacade.getElementFactory(e.getProject()).createImportStaticStatement(PsiTreeUtil.getChildOfType(pf, PsiClass.class), "*");
                            System.out.println("start");
                            System.out.println(piss.getText());
                            System.out.println("end");
                            PsiImportList pil = Utils.getImportList(e.getContainingFile());

                            // TODO this check isn't working
//                            if (!Arrays.asList(pil.getImportStatements()).contains(piss)){
                            if (!Arrays.stream(pil.getImportStaticStatements()).anyMatch(e -> e.getText().contains("spartanizer"))) {
                                pil.add(piss);
                            }
                            e.replace(e_tag);
                        }

                    }.execute();
                }


            }
        };
    }

    @Override
    public Class<PsiConditionalExpression> getPsiClass() {
        return PsiConditionalExpression.class;
    }

    @Override
    protected Tip pattern(final PsiConditionalExpression ¢) {
        return tip(¢);
    }

}

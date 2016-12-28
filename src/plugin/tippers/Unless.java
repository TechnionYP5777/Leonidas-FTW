package plugin.tippers;

import auxilary_layer.*;
import com.google.common.io.Files;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import plugin.tipping.Tip;
import plugin.tipping.Tipper;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class Unless extends NanoPatternTipper<PsiConditionalExpression> {

    @Override
    public boolean canTip(PsiElement e) {
        return (iz.conditionalExpression(e) && (iz.nullExpression(az.conditionalExpression(e).getThenExpression()) || iz.nullExpression(az.conditionalExpression(e).getElseExpression())) && !haz.functionNamed(e.getContainingFile().getNode().getPsi(), "Unless"));
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
                            PsiFile pf = e.getContainingFile().getContainingDirectory().createSubdirectory("spartanizer").createFile("SpartanizerUtils.java");
                            VirtualFile vf = pf.getVirtualFile();

                            URL is = this.getClass().getResource("spartanizer/SpartanizerUtils.java");
                            File file = new File(is.getPath());
                            try {
                                Files.copy(file, new File(vf.getPath()));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
//                            pf = pf.getContainingDirectory().getFiles()[0];

//                            System.out.println(e.getContainingFile().getContainingDirectory().getSubdirectories()[0].getFiles()[0].getText());
//                            System.out.println(e.getContainingFile().getText());

//                            Utils.getImportList(e.getContainingFile()).add(JavaPsiFacade.getElementFactory(e.getProject()).createImportStaticStatement(PsiTreeUtil.getChildOfType(pf, PsiClass.class), "*"));

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

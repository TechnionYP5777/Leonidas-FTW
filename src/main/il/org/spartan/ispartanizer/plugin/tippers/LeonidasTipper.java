package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.PsiElement;
import il.org.spartan.ispartanizer.auxilary_layer.PsiRewrite;
import il.org.spartan.ispartanizer.plugin.leonidas.PsiTreeMatcher;
import il.org.spartan.ispartanizer.plugin.leonidas.PsiTreeTipperBuilder;
import il.org.spartan.ispartanizer.plugin.leonidas.PsiTreeTipperBuilderImpl;
import il.org.spartan.ispartanizer.plugin.leonidas.Replacer;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;
import il.org.spartan.ispartanizer.plugin.tipping.Tipper;

import java.io.File;
import java.io.IOException;

/**
 * Created by melanyc on 1/9/2017.
 */
public class LeonidasTipper implements Tipper<PsiElement> {

    PsiTreeTipperBuilder b = new PsiTreeTipperBuilderImpl();
    File f;

    public LeonidasTipper(File f) {
        this.f = f;
        try {
            b.buildTipperPsiTree(f.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canTip(PsiElement e) {
        return PsiTreeMatcher.match(b.getFromPsiTree(), e);
    }

    @Override
    public String description() {
        return f.getName();
    }

    @Override
    public String description(PsiElement _) {
        return f.getName();
    }

    @Override
    public Tip tip(PsiElement node) {
        return new Tip(description(node), node, this.getClass()) {
            @Override
            public void go(PsiRewrite r) {
                Replacer.replace(node, b, r);
            }
        };
    }

    @Override
    public Class<? extends PsiElement> getPsiClass() {
        return b.getRootElementType();
    }
}

package il.org.spartan.ispartanizer.plugin.tippers.leonidas;

import com.intellij.psi.PsiFile;
import il.org.spartan.ispartanizer.plugin.leonidas.Matcher;
import il.org.spartan.ispartanizer.plugin.leonidas.Replacer;

/**
 * @author Sharon Kuninin
 * @since 26-03-2017.
 */
public class LeonidasTipperDefinition {
    private Matcher m;
    private Replacer r;

    /**
     * @param m matcher
     * @param r replacer
     */
    public LeonidasTipperDefinition(Matcher m, Replacer r) {
        this.m = m;
        this.r = r;
    }

    /**
     * @return a Matcher object representing the template of the tipper.
     */
    public Matcher getMatcher(PsiFile file) {
        return m;
    }

    /**
     * @return Replacer object representing the resulting template of the tipper.
     */
    public Replacer getReplacer(PsiFile file) {
        return r;
    }
}

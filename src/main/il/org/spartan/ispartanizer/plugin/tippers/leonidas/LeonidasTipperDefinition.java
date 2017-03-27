package il.org.spartan.ispartanizer.plugin.tippers.leonidas;

import il.org.spartan.ispartanizer.plugin.leonidas.MatcherBuilder;
import il.org.spartan.ispartanizer.plugin.leonidas.Replacer;

/**
 * @author Sharon Kuninin, Oren Afek
 * @since 26-03-2017.
 */
public interface LeonidasTipperDefinition {
    /**
     *
     * @return
     */

    default MatcherBuilder matcherBuilder() {
        return matcher();
    }

    MatcherBuilder matcher();

    Replacer replacer();
}

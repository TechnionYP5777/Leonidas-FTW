package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.openapi.util.Key;

/**
 * This class helps each place in the code see the same Key object for a specific
 * Key type.
 *
 * @author Michal Cohen
 * @since 2017.01.07
 */
public class KeyDescriptionParameters {

    /**
     * The amount of statements in a code block.
     */
    public static Key<Amount> NO_OF_STATEMENTS = new Key<>(PsiDescriptionParameters.NO_OF_STATEMENTS.name());
    public static Key<Integer> ORDER = new Key<>(PsiDescriptionParameters.ORDER.name());
    public static Key<String> GENERIC_NAME = new Key<>(PsiDescriptionParameters.GENERIC_NAME.name());
}

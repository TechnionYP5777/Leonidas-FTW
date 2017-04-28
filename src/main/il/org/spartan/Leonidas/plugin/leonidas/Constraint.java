package il.org.spartan.Leonidas.plugin.leonidas;

import com.intellij.psi.PsiElement;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;

import static il.org.spartan.Leonidas.plugin.leonidas.Constraint.ConstraintType.IS;
import static il.org.spartan.Leonidas.plugin.leonidas.Constraint.ConstraintType.IS_NOT;

/**
 * Represents a constraint on a generalized variable of the leonidas language.
 * @author michalcohen
 * @since 01-04-2017.
 */
public class Constraint {

    private ConstraintType type;
    private Matcher2 matcher;

    public Constraint(ConstraintType t, EncapsulatingNode e) {
        type = t;
        matcher = new Matcher2();
        matcher.setRoot(e);
    }

    public ConstraintType getType() {
        return type;
    }

    public Matcher2 getMatcher() {
        return matcher;
    }

    /**
     * @param e the users tree to match.
     * @return indication of e being matched recursively to the matcher, when taking in consideration the type of the constraint.
     */
    public boolean match(PsiElement e) {
        return (type == IS && matcher.match(e)) || (type == IS_NOT && !matcher.match(e));
    }

    public enum ConstraintType {
        IS,
        IS_NOT
    }
}

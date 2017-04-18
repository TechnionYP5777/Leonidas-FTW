package il.org.spartan.Leonidas.plugin.leonidas;

import com.intellij.psi.PsiElement;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;

import static il.org.spartan.Leonidas.plugin.leonidas.Constraint.ConstraintType.IS;
import static il.org.spartan.Leonidas.plugin.leonidas.Constraint.ConstraintType.IS_NOT;

/**
 * Created by  on 4/1/2017.
 */
public class Constraint {

    private ConstraintType type;
    private Matcher2 matcher;

    public Constraint(String t, EncapsulatingNode e) {
        type = ConstraintType.valueOf(t);
        matcher = new Matcher2();
        matcher.setRoot(e);
    }

    public Constraint(ConstraintType t, EncapsulatingNode e) {
        type = t;
        matcher = new Matcher2();
        matcher.setRoot(e);
    }

    public Constraint(ConstraintType type) {
        this.type = type;
    }

    public ConstraintType getType() {
        return type;
    }

    public Matcher2 getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher2 m) {
        this.matcher = m;
    }

    public boolean match(PsiElement e) {
        return (type == IS && matcher.match(e)) || (type == IS_NOT && !matcher.match(e));
    }

    public enum ConstraintType {
        IS,
        IS_NOT
    }
}

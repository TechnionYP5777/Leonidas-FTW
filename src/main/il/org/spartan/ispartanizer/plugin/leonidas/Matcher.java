package il.org.spartan.ispartanizer.plugin.leonidas;

import il.org.spartan.ispartanizer.plugin.EncapsulatingNode;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michal Cohen, Sharon Kuninin
 * @since 26-03-2017
 */
public abstract class Matcher {
    private Map<EncapsulatingNode, List<Constraint>> constrains = new HashMap<>();
    private EncapsulatingNode root;

    @Leonidas()
    protected abstract void template();

    /**
     * @param i    the index of the generic type
     * @param type the type of the constraint - but not, and also
     * @param m    - mathcer
     */
    private void addConstraint(int i, ConstraintType type, Matcher m) {
        EncapsulatingNode e = root.accept(e1 -> e1.getInner().getUserData(KeyDescriptionParameters.ID) == i ? e1 : null, (a, b) -> a);
        constrains.putIfAbsent(e, new ArrayList<>());
        constrains.get(e).add(new Constraint(type, m));
    }

    /**
     * insert new constraint on the generic element described by but not  m
     *
     * @param i - the index of the generic element
     * @param m - matcher
     * @return the current matcher for fluent code
     */
    public Matcher butNot(int i, Matcher m) {
        addConstraint(i, ConstraintType.BUT_NOT, m);
        return this;
    }

    /**
     * insert new constraint on the generic element described by and also m
     *
     * @param i - the index of the generic element
     * @param m - matcher
     * @return the current matcher for fluent code
     */
    public Matcher andAlso(int i, Matcher m) {
        addConstraint(i, ConstraintType.AND_ALSO, m);
        return this;
    }

    /**
     * @param e The code of the user
     * @return true iff the code of the user matches the constarins in the matcher
     */
    public boolean match(EncapsulatingNode e) {
        if (!PsiTreeMatcher.match(root, e)) return false;

        return constrains.entrySet().stream().map(entry -> entry.getValue().stream().map(c -> {
            switch (c.getType()) {
                case BUT_NOT:
                    return !c.getMatcher().match(entry.getKey());
                case AND_ALSO:
                    return c.getMatcher().match(entry.getKey());
                default:
                    return false;
            }
        }).reduce((b1, b2) -> b1 && b2).get()).reduce((b1, b2) -> b1 && b2).get();
    }

    /**
     * TODO
     *
     * @return the template in the matcher.
     */
    public EncapsulatingNode getTemplate() {
        String path = Paths.get("").toAbsolutePath().toString();
        File sourceFile = new File(String.format("%s\\%s.java", path, getClass().getSimpleName()));

        return null;
    }

    private enum ConstraintType {
        BUT_NOT,
        AND_ALSO
    }

    private class Constraint {
        private ConstraintType type;
        private Matcher matcher;

        public Constraint(ConstraintType type, Matcher matcher) {
            this.type = type;
            this.matcher = matcher;
        }

        public ConstraintType getType() {
            return type;
        }

        public Matcher getMatcher() {
            return matcher;
        }
    }
}

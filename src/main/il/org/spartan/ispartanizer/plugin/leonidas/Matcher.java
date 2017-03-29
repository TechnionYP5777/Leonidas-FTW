package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import il.org.spartan.ispartanizer.auxilary_layer.Wrapper;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.EncapsulatingNode;
import il.org.spartan.ispartanizer.plugin.tippers.leonidas.LeonidasTipperDefinition;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Michal Cohen, Sharon Kuninin
 * @since 26-03-2017
 */
public class Matcher extends GenericPsiElementStub {
    private static final String LEONIDAS_ANNOTATION_NAME = "il.org.spartan.ispartanizer.plugin.leonidas.Leonidas";
    private static final String SHORT_LEONIDAS_ANNOTATION_NAME = "Leonidas";
    private static final String LEONIDAS_ANNOTATION_VALUE = "value";
    private static String TEMPLATE = "template";
    Stack<EncapsulatingNode> sourceOfConstraints = new Stack<>();
    private Map<Integer, List<Constraint>> constrains = new HashMap<>();
    private EncapsulatingNode root;

    /**
     * @param x the template method
     * @return extract the first PsiElement of the type described in the Leonidas annotation
     */
    private Class<? extends PsiElement> getPsiElementTypeFromAnnotation(PsiMethod x) {
        return Arrays.stream(x.getModifierList().getAnnotations())
                .filter(a -> LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()) || SHORT_LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()))
                .map(a -> getAnnotationClass(a.findDeclaredAttributeValue(LEONIDAS_ANNOTATION_VALUE).getText().replace(".class", "")))
                .findFirst().get();
    }

    /**
     * @param s the name of the PsiElement inherited class
     * @return the .class of s.
     */
    private Class<? extends PsiElement> getAnnotationClass(String s) {
        try {
            //noinspection unchecked
            return (Class<? extends PsiElement>) Class.forName("com.intellij.psi." + s);
        } catch (ClassNotFoundException ignore) {
        }
        return PsiElement.class;
    }

    /**
     * @param method          the template method
     * @param rootElementType the type of the first PsiElement in the wanted tree
     * @return the first PsiElement of the type rootElementType
     */
    private PsiElement getTreeFromRoot(PsiMethod method, Class<? extends PsiElement> rootElementType) {
        Wrapper<PsiElement> result = new Wrapper<>();
        Wrapper<Boolean> stop = new Wrapper<>(false);
        method.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                if (!stop.get() && iz.ofType(element, rootElementType)) {
                    result.set(element);
                    stop.set(true);
                }
            }
        });
        return result.get();
    }

    //TODO correct
    public Matcher initialzeSourceCode(PsiFile file) {
        file.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethod(PsiMethod method) {
                super.visitMethod(method);
                if (!method.getName().equals(TEMPLATE)) return;
                sourceOfConstraints.push(Pruning.prune(EncapsulatingNode.buildTreeFromPsi(getTreeFromRoot(method,
                        getPsiElementTypeFromAnnotation(method)))));

            }
        });
        root = sourceOfConstraints.pop();
        return this;
    }

    //TODO
    public Matcher build() {
        return null;
    }

    /**
     * @param i        the index of the generic type
     * @param type     the type of the constraint - but not, and also
     * @param template - template
     */
    private void addConstraint(int i, ConstraintType type, EncapsulatingNode template) {
        constrains.putIfAbsent(i, new ArrayList<>());
        constrains.get(i).add(new Constraint(type, template));
    }


    /**
     * insert new constraint on the generic element described by but not  m
     *
     * @param i        - the index of the generic element
     * @param template - template
     * @return the current matcher for fluent code
     */
    public Matcher butNot(int i, Template template) {
        addConstraint(i, ConstraintType.BUT_NOT, sourceOfConstraints.pop());
        return this;
    }

    /**
     * insert new constraint on the generic element described by and also m
     *
     * @param i        - the index of the generic element
     * @param template - Template to match
     * @return the current matcher for fluent code
     */
    public Matcher andAlso(int i, Template template) {
        addConstraint(i, ConstraintType.AND_ALSO, sourceOfConstraints.pop());
        return this;
    }

    /**
     * @param e The code of the user
     * @return true iff the code of the user matches the constarins in the matcher
     */
    public boolean match(EncapsulatingNode e, PsiElement r) {
        if (!PsiTreeMatcher.match(root, e)) return false;

//        return constrains.entrySet().stream().map(entry -> entry.getValue().stream().map(c -> {
//            switch (c.getType()) {
//                case BUT_NOT:
//                    return !c.getMatcher().match(entry.getKey());
//                case AND_ALSO:
//                    return c.getMatcher().match(entry.getKey());
//                default:
//                    return false;
//            }
//        }).reduce((b1, b2) -> b1 && b2).get()).reduce((b1, b2) -> b1 && b2).get();

        return false; // TODO
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
        private EncapsulatingNode template;

        public Constraint(ConstraintType type, EncapsulatingNode template) {
            this.type = type;
            this.template = template;
        }

        public ConstraintType getType() {
            return type;
        }

        public EncapsulatingNode getTemplate() {
            return template;
        }


    }
}

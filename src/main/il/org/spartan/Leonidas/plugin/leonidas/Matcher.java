package il.org.spartan.Leonidas.plugin.leonidas;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import il.org.spartan.Leonidas.auxilary_layer.Wrapper;
import il.org.spartan.Leonidas.auxilary_layer.iz;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Michal Cohen, Sharon Kuninin
 * @since 26-03-2017
 */
public class Matcher extends GenericPsiElementStub {
    private static final String LEONIDAS_ANNOTATION_NAME = "il.org.spartan.Leonidas.plugin.leonidas.Leonidas";
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
     * @param m          the template method
     * @param rootElementType the type of the first PsiElement in the wanted tree
     * @return the first PsiElement of the type rootElementType
     */
    private PsiElement getTreeFromRoot(PsiMethod m, Class<? extends PsiElement> rootElementType) {
        Wrapper<PsiElement> result = new Wrapper<>();
        Wrapper<Boolean> stop = new Wrapper<>(false);
        m.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement e) {
                super.visitElement(e);
                if (stop.get() || !iz.ofType(e, rootElementType))
					return;
				result.set(e);
				stop.set(true);
            }
        });
        return result.get();
    }

    //TODO correct
    public Matcher initialzeSourceCode(PsiFile f) {
        f.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethod(PsiMethod m) {
                super.visitMethod(m);
                if (m.getName().equals(TEMPLATE))
					sourceOfConstraints.push(Pruning.prune(EncapsulatingNode
							.buildTreeFromPsi(getTreeFromRoot(m, getPsiElementTypeFromAnnotation(m)))));

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
     * @param t     the type of the constraint - but not, and also
     * @param template - template
     */
    private void addConstraint(int i, ConstraintType t, EncapsulatingNode template) {
        constrains.putIfAbsent(i, new ArrayList<>());
        constrains.get(i).add(new Constraint(t, template));
    }


    /**
     * insert new constraint on the generic element described by but not  m
     *
     * @param i        - the index of the generic element
     * @param t - template
     * @return the current matcher for fluent code
     */
    public Matcher butNot(int i, Template t) {
        addConstraint(i, ConstraintType.BUT_NOT, sourceOfConstraints.pop());
        return this;
    }

    /**
     * insert new constraint on the generic element described by and also m
     *
     * @param i        - the index of the generic element
     * @param t - Template to match
     * @return the current matcher for fluent code
     */
    public Matcher andAlso(int i, Template t) {
        addConstraint(i, ConstraintType.AND_ALSO, sourceOfConstraints.pop());
        return this;
    }

    /**
     * @param n The code of the user
     * @return true iff the code of the user matches the constarins in the matcher
     */
    public boolean match(EncapsulatingNode n, PsiElement r) {
		return PsiTreeMatcher.match(root, n) && false;
	}

    /**
     * TODO
     *
     * @return the template in the matcher.
     */
    public EncapsulatingNode getTemplate() {
        String path = Paths.get("").toAbsolutePath().toString();
        new File(String.format("%s\\%s.java", path, getClass().getSimpleName()));
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

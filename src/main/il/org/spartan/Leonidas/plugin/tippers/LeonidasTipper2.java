package il.org.spartan.Leonidas.plugin.tippers;

import com.google.common.io.Files;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import il.org.spartan.Leonidas.auxilary_layer.*;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;
import il.org.spartan.Leonidas.plugin.leonidas.Constraint;
import il.org.spartan.Leonidas.plugin.leonidas.GenericPsiTypes.Replacer2;
import il.org.spartan.Leonidas.plugin.leonidas.Matcher2;
import il.org.spartan.Leonidas.plugin.leonidas.Pruning;
import il.org.spartan.Leonidas.plugin.tipping.Tip;
import il.org.spartan.Leonidas.plugin.tipping.Tipper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This class represents a tipper created by the leonidas language.
 *
 * @author Amir Sagiv, Sharon Kuninin, michalcohen
 * @since 26-03-2017.
 */
public class LeonidasTipper2 implements Tipper<PsiElement> {

    private static final String LEONIDAS_ANNOTATION_NAME = "il.org.spartan.Leonidas.plugin.leonidas.Leonidas";
    private static final String SHORT_LEONIDAS_ANNOTATION_NAME = "Leonidas";
    private static final String LEONIDAS_ANNOTATION_VALUE = "value";


    String description;


    Matcher2 matcher;

    Replacer2 replacer;
    Class<? extends PsiElement> rootType;
    PsiFile file;
    Map<Integer, List<Constraint>> map;

    @SuppressWarnings("ConstantConditions")
    public LeonidasTipper2(String tipperName, String fileContent) throws IOException {
        //file = getPsiTreeFromFile(f);
          file = getPsiTreeFromString("roei_oren" + tipperName, fileContent);
        description = /*Utils.getClassFromFile(file).getDocComment().getText()
                .split("\\n")[1].trim()
                .split("\\*")[1].trim();*/ "";
        matcher = new Matcher2();
        matcher.setRoot(getMatcherRootTree());
        map = getConstraints();
        buildMatcherTree(matcher);
        replacer = new Replacer2(matcher, getReplacerRootTree());
        rootType = getPsiElementTypeFromAnnotation(getInterfaceMethod("matcher"));
    }

    private Integer extractId(PsiStatement s) {
        Wrapper<PsiMethodCallExpression> x = new Wrapper<>();
        s.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (expression.getMethodExpression().getText().equals("the")) {
                    x.set(az.methodCallExpression(expression.getArgumentList().getExpressions()[0]));
                }
            }
        });
        PsiMethodCallExpression m = x.get();
        return Integer.parseInt(m.getArgumentList().getExpressions()[0].getText());

    }

    private String extractConstraintType(PsiStatement s) {
        Wrapper<Boolean> r = new Wrapper<>(Boolean.TRUE);
        s.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitReferenceExpression(PsiReferenceExpression x) {
                super.visitReferenceExpression(x);
                if (x.getText().endsWith("isNot"))
					r.set(false);
            }
        });
        return r.get() ? "is" : "isNot";
    }

    private PsiElement getLambdaExpressionBody(PsiStatement s) {
        Wrapper<PsiLambdaExpression> l = new Wrapper<>();
        s.accept(new JavaRecursiveElementVisitor() {

            @Override
            public void visitLambdaExpression(PsiLambdaExpression x) {
                super.visitLambdaExpression(x);
                l.set(x);
            }
        });
        return l.get().getBody();
    }

    private Optional<Class<? extends PsiElement>> getTypeOf(PsiStatement s) {
        Wrapper<Class<? extends PsiElement>> q = new Wrapper<>();
        s.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression x) {
                super.visitMethodCallExpression(x);
                if ("ofType".equals(x.getMethodExpression().getText()))
					q.set(getPsiClass(x.getArgumentList().getExpressions()[0].getText().replace(".class", "")));
            }
        });
        return Optional.of(q.get());
    }

    private Map<Integer, List<Constraint>> getConstraints() {
        Map<Integer, List<Constraint>> map = new HashMap<>();
        PsiMethod constrainsMethod = getInterfaceMethod("constraints");
        if (haz.body(constrainsMethod))
			Arrays.stream(constrainsMethod.getBody().getStatements()).forEach(s -> {
				Integer key = extractId(s);
				PsiElement y = getLambdaExpressionBody(s);
				Optional<Class<? extends PsiElement>> q = getTypeOf(s);
				y = !q.isPresent() ? y : getRealRootByType(y, q.get());
				map.putIfAbsent(key, new LinkedList<>());
				map.get(key).add(new Constraint(extractConstraintType(s), EncapsulatingNode.buildTreeFromPsi(y)));
			});

        return map;
    }

    private Class<? extends PsiElement> getPsiClass(String s) {
        try {
            //noinspection unchecked
            return (Class<? extends PsiElement>) Class.forName("com.intellij.psi." + s);
        } catch (ClassNotFoundException ignore) {
        }
        return PsiElement.class;
    }

    private PsiElement getRealRootByType(PsiElement e, Class<? extends PsiElement> rootElementType) {
        Wrapper<PsiElement> result = new Wrapper<>();
        Wrapper<Boolean> stop = new Wrapper<>(false);
        e.accept(new JavaRecursiveElementVisitor() {
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

    private void buildMatcherTree(Matcher2 m) {
        List<Integer> l = m.getGenericElements();
        l.stream().forEach(i -> map.get(i).stream().forEach(j ->
                m.addConstraint(i, j)));
        l.stream().forEach(i ->
                m.getMap().get(i).stream().map(Constraint::getMatcher)
                        .forEach(this::buildMatcherTree));
    }

    private PsiMethod getInterfaceMethod(String name) {
        Wrapper<PsiMethod> x = new Wrapper<>();
        file.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethod(PsiMethod m) {
                super.visitMethod(m);
                if (m.getName().equals(name))
					x.set(m);
            }
        });
        return x.get();
    }

    @Override
    public boolean canTip(PsiElement e) {
        return matcher.match(e);
    }

    @Override
    public String description(PsiElement e) {
        return description;
    }

    @Override
    public Tip tip(PsiElement node) {
        return new Tip(description(node), node, this.getClass()) {
            @Override
            public void go(PsiRewrite r) {
                if (canTip(node))
					replacer.replace(node, matcher.extractInfo(node), r);
            }
        };
    }

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

    /**
     * @return the generic tree representing the "from" template
     */
    private EncapsulatingNode getMatcherRootTree() {
        return Pruning.prune(EncapsulatingNode.buildTreeFromPsi(getTreeFromRoot(getInterfaceMethod("matcher"),
                getPsiElementTypeFromAnnotation(getInterfaceMethod("matcher")))));
    }

    /**
     * @return the generic tree representing the "from" template
     */
    private EncapsulatingNode getReplacerRootTree() {
        return Pruning.prune(EncapsulatingNode.buildTreeFromPsi(getTreeFromRoot(getInterfaceMethod("matcher"),
                getPsiElementTypeFromAnnotation(getInterfaceMethod("replacer")))));
    }

    @Override
    public Class<? extends PsiElement> getPsiClass() {
        return rootType;
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
     * @param f the LeonidasTipperDefinition file.
     * @return PsiFile element representing the given file
     * @throws IOException - if the file could not be opened or read.
     */
    private PsiFile getPsiTreeFromFile(File f)  throws IOException {
        return PsiFileFactory.getInstance(Utils.getProject())
                .createFileFromText(f.getName(), FileTypeRegistry.getInstance().getFileTypeByFileName(f.getName()),
                        String.join("\n", Files.readLines(f, StandardCharsets.UTF_8)));
    }

    private PsiFile getPsiTreeFromString(String psiFileName, String s){
        return PsiFileFactory.getInstance(Utils.getProject())
                .createFileFromText(JavaLanguage.INSTANCE, s);
    }
}

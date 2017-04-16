package il.org.spartan.leonidas.plugin.tippers;

import com.google.common.io.Files;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import il.org.spartan.leonidas.auxilary_layer.*;
import il.org.spartan.leonidas.plugin.EncapsulatingNode;
import il.org.spartan.leonidas.plugin.leonidas.Constraint;
import il.org.spartan.leonidas.plugin.leonidas.GenericPsiTypes.Replacer2;
import il.org.spartan.leonidas.plugin.leonidas.Matcher2;
import il.org.spartan.leonidas.plugin.leonidas.Pruning;
import il.org.spartan.leonidas.plugin.tipping.Tip;
import il.org.spartan.leonidas.plugin.tipping.Tipper;

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

    private static final String LEONIDAS_ANNOTATION_NAME = "il.org.spartan.leonidas.plugin.leonidas.leonidas";
    private static final String SHORT_LEONIDAS_ANNOTATION_NAME = "leonidas";
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
            public void visitReferenceExpression(PsiReferenceExpression expression) {
                super.visitReferenceExpression(expression);
                if (expression.getText().endsWith("isNot")) {
                    r.set(false);
                }
            }
        });
        return r.get() ? "is" : "isNot";
    }

    private PsiElement getLambdaExpressionBody(PsiStatement s) {
        Wrapper<PsiLambdaExpression> l = new Wrapper<>();
        s.accept(new JavaRecursiveElementVisitor() {

            @Override
            public void visitLambdaExpression(PsiLambdaExpression expression) {
                super.visitLambdaExpression(expression);
                l.set(expression);
            }
        });
        return l.get().getBody();
    }

    private Optional<Class<? extends PsiElement>> getTypeOf(PsiStatement s) {
        Wrapper<Class<? extends PsiElement>> q = new Wrapper<>();
        s.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (expression.getMethodExpression().getText().equals("ofType")) {
                    q.set(getPsiClass(expression.getArgumentList().getExpressions()[0].getText().replace(".class", "")));
                }
            }
        });
        return Optional.of(q.get());
    }

    private Map<Integer, List<Constraint>> getConstraints() {
        Map<Integer, List<Constraint>> map = new HashMap<>();
        PsiMethod constrainsMethod = getInterfaceMethod("constraints");
        if(!haz.body(constrainsMethod)){
            return map;
        }

        Arrays.stream(constrainsMethod.getBody().getStatements()).forEach(s -> {
            Integer key = extractId(s);
            PsiElement y = getLambdaExpressionBody(s);
            Optional<Class<? extends PsiElement>> q = getTypeOf(s);
            y = q.isPresent() ? getRealRootByType(y, q.get()) : y;
            // y - root, key ID
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

    private PsiElement getRealRootByType(PsiElement element, Class<? extends PsiElement> rootElementType) {
        Wrapper<PsiElement> result = new Wrapper<>();
        Wrapper<Boolean> stop = new Wrapper<>(false);
        element.accept(new JavaRecursiveElementVisitor() {
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
            public void visitMethod(PsiMethod method) {
                super.visitMethod(method);
                if (method.getName().equals(name)) {
                    x.set(method);
                }
            }
        });
        return x.get();
    }

    @Override
    public boolean canTip(PsiElement e) {
        return matcher.match(e);
    }

    @Override
    public String description(PsiElement element) {
        return description;
    }

    @Override
    public Tip tip(PsiElement node) {
        return new Tip(description(node), node, this.getClass()) {
            @Override
            public void go(PsiRewrite r) {
                if (!canTip(node)) return;
                replacer.replace(node, matcher.extractInfo(node), r);
            }
        };
    }

    /**
     * @param x the template method
     * @return extract the first PsiElement of the type described in the leonidas annotation
     */
    private Class<? extends PsiElement> getPsiElementTypeFromAnnotation(PsiMethod x) {
        return Arrays.stream(x.getModifierList().getAnnotations())
                .filter(a -> LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()) || SHORT_LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()))
                .map(a -> getAnnotationClass(a.findDeclaredAttributeValue(LEONIDAS_ANNOTATION_VALUE).getText().replace(".class", "")))
                .findFirst().get();
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
     * @param file the LeonidasTipperDefinition file.
     * @return PsiFile element representing the given file
     * @throws IOException - if the file could not be opened or read.
     */
    private PsiFile getPsiTreeFromFile(File file)  throws IOException {
        return PsiFileFactory.getInstance(Utils.getProject())
                .createFileFromText(file.getName(), FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName()),
                        String.join("\n", Files.readLines(file, StandardCharsets.UTF_8)));
    }

    private PsiFile getPsiTreeFromString(String psiFileName, String s){
        return PsiFileFactory.getInstance(Utils.getProject())
                .createFileFromText(JavaLanguage.INSTANCE, s);
    }
}

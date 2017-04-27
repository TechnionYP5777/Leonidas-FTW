package il.org.spartan.Leonidas.plugin.tippers;

import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.*;
import com.intellij.testFramework.LightVirtualFile;
import il.org.spartan.Leonidas.auxilary_layer.*;
import il.org.spartan.Leonidas.plugin.EncapsulatingNode;
import il.org.spartan.Leonidas.plugin.leonidas.*;
import il.org.spartan.Leonidas.plugin.tipping.Tip;
import il.org.spartan.Leonidas.plugin.tipping.Tipper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import static il.org.spartan.Leonidas.plugin.leonidas.KeyDescriptionParameters.ID;

/**
 * This class represents a tipper created by the leonidas language.
 *
 * @author Amir Sagiv, Sharon Kuninin, michalcohen
 * @since 26-03-2017.
 */
public class LeonidasTipper2 implements Tipper<PsiElement> {

    private static final String LEONIDAS_ANNOTATION_NAME = Leonidas.class.getTypeName();
    private static final String SHORT_LEONIDAS_ANNOTATION_NAME = Leonidas.class.getSimpleName();
    private static final String LEONIDAS_ANNOTATION_VALUE = "value";

    String description;
    Matcher2 matcher;
    Replacer2 replacer;
    Class<? extends PsiElement> rootType;
    PsiJavaFile file;
    Map<Integer, List<Constraint>> map;

    @SuppressWarnings("ConstantConditions")
    public LeonidasTipper2(String tipperName, String fileContent) throws IOException {
        file = getPsiTreeFromString("Tipper" + tipperName, fileContent);
        PsiClass c = Utils.getClassFromFile(file);
        description = c.getDocComment().getText()
                .split("\\n")[1].trim()
                .split("\\*")[1].trim();

        matcher = new Matcher2(getMatcherRootTree());
        map = getConstraints();
        buildMatcherTree(matcher);
        replacer = new Replacer2(matcher, getReplacerRootTree());
        rootType = getPsiElementTypeFromAnnotation(getInterfaceMethod("matcher"));
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new LinkedList<>();
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
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

    /**
     * @param s constraint statement.
     * @return "is" if the(index).is(constraint) and "isNot" if the(index).isNot(constraint).
     */
    private Constraint.ConstraintType extractConstraintType(PsiStatement s) {
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
        return r.get() ? Constraint.ConstraintType.IS : Constraint.ConstraintType.IS_NOT;
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

    // Example of code that documents itself. In this case, it documents "DON'T TOUCH ME".
    private Optional<Class<? extends PsiElement>> getTypeOf(PsiStatement s) {
        Wrapper<Optional<Class<? extends PsiElement>>> wq = new Wrapper<>(Optional.empty());
        s.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (expression.getMethodExpression().getText().endsWith("ofType")) {
                    wq.set(Optional.of(getPsiClass(expression.getArgumentList().getExpressions()[0].getText().replace(".class", ""))));
                }
            }
        });
        return wq.get();
    }

    private Map<Integer, List<Constraint>> getConstraints() {
        Map<Integer, List<Constraint>> map = new HashMap<>();
        PsiMethod constrainsMethod = getInterfaceMethod("constraints");
        if (!haz.body(constrainsMethod)) {
            return map;
        }

        Arrays.stream(constrainsMethod.getBody().getStatements()).forEach(s -> {
            Integer key = extractId(s);
            PsiElement y = getLambdaExpressionBody(s);
            Optional<Class<? extends PsiElement>> q = getTypeOf(s);
            y = q.isPresent() ? getRealRootByType(y, q.get()) : y;
            // y - root, key ID
            map.putIfAbsent(key, new LinkedList<>());
            giveIdToStubMethodCalls(y);
            map.get(key).add(new Constraint(extractConstraintType(s), Pruning.prune(EncapsulatingNode.buildTreeFromPsi(y))));
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
        Set<Integer> l = m.getGenericElements();
        l.stream().forEach(i -> Optional.ofNullable(map.get(i)).ifPresent(z -> z.stream().forEach(j ->
                m.addConstraint(i, j))));
        m.getConstraintsMatchers().stream().forEach(this::buildMatcherTree);
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
     * @return extract the first PsiElement of the type described in the Leonidas annotation
     */
    private Class<? extends PsiElement> getPsiElementTypeFromAnnotation(PsiMethod x) {
        return Arrays.stream(x.getModifierList().getAnnotations()) //
                .filter(a -> LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()) //
                        || SHORT_LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName())) //
                .map(a -> getAnnotationClass(a.findDeclaredAttributeValue(LEONIDAS_ANNOTATION_VALUE) //
                        .getText().replace(".class", ""))) //
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

    private void giveIdToStubMethodCalls(PsiElement innerTree) {
        innerTree.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                if (!iz.stubMethodCall(expression)) {
                    return;
                }
                expression.putUserData(ID, az.integer(step.firstParamterExpression(expression)));
            }
        });
    }

    /**
     * @return the generic tree representing the "from" template
     */
    private EncapsulatingNode getMatcherRootTree() {

        PsiMethod method = getInterfaceMethod("matcher");
        giveIdToStubMethodCalls(method);

        return Pruning.prune(EncapsulatingNode.buildTreeFromPsi(getTreeFromRoot(method,
                getPsiElementTypeFromAnnotation(method))));
    }

    /**
     * @return the generic tree representing the "from" template
     */
    private EncapsulatingNode getReplacerRootTree() {

        PsiMethod matcher = getInterfaceMethod("matcher");
        PsiMethod replacer = getInterfaceMethod("replacer");
        giveIdToStubMethodCalls(matcher);
        giveIdToStubMethodCalls(replacer);
        return Pruning.prune(EncapsulatingNode.buildTreeFromPsi(getTreeFromRoot(matcher,
                getPsiElementTypeFromAnnotation(replacer))));
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
     * @param name    the name of the tipper
     * @param content the definition file of the tipper
     * @return PsiFile representing the file of the tipper
     */
    private PsiJavaFile getPsiTreeFromString(String name, String content) {
        Language language = JavaLanguage.INSTANCE;
        LightVirtualFile virtualFile = new LightVirtualFile(name, language, content);
        SingleRootFileViewProvider.doNotCheckFileSizeLimit(virtualFile);
        final FileViewProviderFactory factory = LanguageFileViewProviders.INSTANCE.forLanguage(language);
        FileViewProvider viewProvider = factory != null ? factory.createFileViewProvider(virtualFile, language, Utils.getPsiManager(Utils.getProject()), true) : null;
        if (viewProvider == null)
            viewProvider = new SingleRootFileViewProvider(
                    Utils.getPsiManager(ProjectManager.getInstance().getDefaultProject()),
                    virtualFile,
                    true
            );
        language = viewProvider.getBaseLanguage();
        final ParserDefinition parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language);
        if (parserDefinition != null) {
            return (PsiJavaFile) viewProvider.getPsi(language);
        }
        return null;
    }

    public void fixTheFuckupsOfIntellij_IamNotFrikingDeamon() {
        try {
            ProgressIndicator obj = ProgressManager.getInstance().getProgressIndicator();
            ProgressManager man = ProgressManager.getInstance();
            List<Field> fields = getAllFields(obj.getClass());
            List<Field> fields2 = getAllFields(man.getClass());
            Field myCanceled = fields.stream().filter(f -> f.getName().equals("myCanceled")).findFirst().get();
            Field shouldCancel = fields2.stream().filter(f -> f.getName().equals("shouldCheckCanceled")).findFirst().get();
            myCanceled.setAccessible(true);
            shouldCancel.setAccessible(true);
            try {
                myCanceled.set(obj, false);
                shouldCancel.set(obj, false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (Exception ignore) {

        }
        //Boolean b = ProgressIndicatorProvider.getInstance().getProgressIndicator().isCanceled();
    }
}

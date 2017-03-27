package il.org.spartan.ispartanizer.plugin.leonidas;

import com.google.common.io.Files;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import il.org.spartan.ispartanizer.auxilary_layer.*;
import il.org.spartan.ispartanizer.plugin.EncapsulatingNode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static il.org.spartan.ispartanizer.plugin.leonidas.KeyDescriptionParameters.ID;

/**
 * @author Oren Afek
 * @since 06-01-2017
 */
public class PsiTreeTipperBuilderImpl implements PsiTreeTipperBuilder {

    private static final String FILE_PATH = "/spartanizer/LeonidasTippers/";
    private static final String FROM_METHOD_NAME = "from";
    private static final String TO_METHOD_NAME = "to";
    private static final String LEONIDAS_ANNOTATION_NAME = "il.org.spartan.ispartanizer.plugin.leonidas.Leonidas";
    private static final String SHORT_LEONIDAS_ANNOTATION_NAME = "Leonidas";
    private static final String LEONIDAS_ANNOTATION_ORDER = "order";
    private static final String PSI_PACKAGE_PREFIX = "com.intellij.psi.";
    private static final String LEONIDAS_ANNOTATION_VALUE = "value";

    static boolean tmp1 = false;
    private boolean built = false;
    private EncapsulatingNode fromTree;
    private EncapsulatingNode toTree;
    private Class<? extends PsiElement> fromRootElementType;
    private String description;
    private int defId = 0;
    private Map<Integer, Integer> mapToDef = new HashMap<>();

    /**
     * Build both the "from" and "to" trees from source code, including pruning.
     *
     * @param fileName - the file name of the Leonidas tipper to build.
     * @return @link{this}
     * @throws IOException in case the file could not be opened.
     */
    @SuppressWarnings("ConstantConditions")
    public PsiTreeTipperBuilderImpl buildTipperPsiTree(String fileName) throws IOException {
        assert (!built);
        PsiFile root = getPsiTreeFromFile(fileName);
        description = Utils.getClassFromFile(root).getDocComment().getText()
                .split("\\n")[1].trim()
                .split("\\*")[1].trim();
        fromTree = buildMethodTree(root, FROM_METHOD_NAME);
        toTree = buildMethodTree(root, TO_METHOD_NAME);
        built = true;
        return this;
    }

    private EncapsulatingNode buildMethodTree(PsiFile root, String methodName) {
        PsiMethod method = getMethodFromTree(root, methodName);
        Class<? extends PsiElement> rootType = getPsiElementTypeFromAnnotation(method);
        if (methodName.equals(FROM_METHOD_NAME)) {
            fromRootElementType = rootType;
        }
        PsiElement tree = getTreeFromRoot(method, rootType);
        handleStubMethodCalls(tree, methodName);
        EncapsulatingNode e = EncapsulatingNode.buildTreeFromPsi(tree);
        pruneStubChildren(e);
        return e;
    }

    /**
     * Retrieving the "from" tree. This method should only be called after
     *
     * @return the "from" tree
     * @link {@link PsiTreeTipperBuilder}.buildTipperPsiTree was called.
     */
    @Override
    public EncapsulatingNode getFromPsiTree() {
        assert (built);
        return fromTree;
    }

    /**
     * Retrieving the "to" tree. This method should only be called after
     *
     * @return the "to" tree
     * @link {@link PsiTreeTipperBuilder}.buildTipperPsiTree was called.
     */
    @Override
    public EncapsulatingNode getToPsiTree() {
        assert (built);
        return toTree.clone();
    }

    private PsiFile getPsiTreeFromFile(String fileName) throws IOException {
        File file = new File(Utils.fixSpacesProblemOnPath(this.getClass().getResource(FILE_PATH + fileName).getPath()));
        return PsiFileFactory.getInstance(Utils.getProject()).createFileFromText(fileName,
                FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName()),
                String.join("\n", Files.readLines(file, StandardCharsets.UTF_8)));
    }

    private PsiMethod getMethodFromTree(PsiFile file, String methodName) {
        Wrapper<PsiMethod> result = new Wrapper<>();
        file.accept(new JavaRecursiveElementVisitor() {

            @Override
            public void visitMethod(PsiMethod method) {
                if (step.name(method).equals(methodName)) {
                    result.set(method);
                }
            }
        });
        return result.get();
    }

    private Class<? extends PsiElement> getPsiClass(String s) {
        try {
            //noinspection unchecked
            return (Class<? extends PsiElement>) Class.forName("com.intellij.psi." + s);
        } catch (ClassNotFoundException ignore) {
        }
        return PsiElement.class;
    }

    @SuppressWarnings({"ConstantConditions", "OptionalGetWithoutIsPresent"})
    private Class<? extends PsiElement> getPsiElementTypeFromAnnotation(PsiMethod x) {
        return Arrays.stream(x.getModifierList().getAnnotations())
                .filter(a -> LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()) || SHORT_LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()))
                .map(a -> getPsiClass(a.findDeclaredAttributeValue(LEONIDAS_ANNOTATION_VALUE).getText().replace(".class", "")))
                .findFirst().get();
    }

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

    private void handleStubMethodCalls(PsiElement innerTree, String outerMethodName) {
        innerTree.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                if (!iz.stubMethodCall(expression)) {
                    return;
                }
                Integer id;
                if (outerMethodName.equals(FROM_METHOD_NAME)) {
                    if (step.firstParamterExpression(expression) != null) {
                        mapToDef.put(az.integer(step.firstParamterExpression(expression)), defId);
                    }
                    id = defId++;
                } else {
                    //TODO: please explain the following line. @orenafek
                    id = step.firstParamterExpression(expression) != null ? mapToDef.get(az.integer(step.firstParamterExpression(expression))) : defId++;
                }
                addOrderToUserData(expression, id);
            }
        });
    }

    private void pruneStubChildren(EncapsulatingNode innerTree) {
        Pruning.prune(innerTree);
    }

    private PsiElement addOrderToUserData(PsiElement element, int order) {
        element.putUserData(ID, order);
        return element;
    }

    @Override
    public Class<? extends PsiElement> getRootElementType() {
        return fromRootElementType;
    }

    @Override
    public String getDescription() {
        return description;
    }

}

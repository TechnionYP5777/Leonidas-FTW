package il.org.spartan.ispartanizer.plugin.leonidas;

import com.google.common.io.Files;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import il.org.spartan.ispartanizer.auxilary_layer.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static il.org.spartan.ispartanizer.plugin.leonidas.KeyDescriptionParameters.ID;

/**
 * @author Oren Afek
 * @since 06-01-2017
 */
public class PsiTreeTipperBuilderImpl implements PsiTreeTipperBuilder {

    private static final String FILE_PATH = "/spartanizer/LeonidasTippers/";
    private static final String FROM_METHOD_NAME = "from";
    private static final String TO_METHOD_NAME = "to";
    private static final String LEONIDAS_ANNOTATION_NAME = Leonidas.class.getSimpleName();
    private static final String LEONIDAS_ANNOTATION_VALUE = "value";
    private static final String LEONIDAS_ANNOTATION_ORDER = "order";
    private static final String PSI_PACKAGE_PREFIX = "com.intellij.psi.";
    private boolean built;
    private PsiElement fromTree;
    private PsiElement toTree;
    private Class<? extends PsiElement> fromRootType;

    public PsiTreeTipperBuilderImpl() {
        built = false;
    }

    /**
     * Build both the "from" and "to" trees from source code, including pruning.
     *
     * @param fileName - the file name of the Leonidas tipper to build.
     * @return @link{this}
     * @throws IOException in case the file could not be opened.
     */
    public PsiTreeTipperBuilderImpl buildTipperPsiTree(String fileName) throws IOException {
        assert (!built);
        PsiFile root = getPsiTreeFromFile(fileName);
        PsiMethod from = getMethodFromTree(root, FROM_METHOD_NAME);
        PsiMethod to = getMethodFromTree(root, TO_METHOD_NAME);
        fromRootType = getPsiElementTypeFromAnnotation(from);
        fromTree = getTreeFromRoot(from, fromRootType);
        handleStubMethodCalls(fromTree);
        pruneStubChildren(fromTree);
        toTree = getTreeFromRoot(to, getPsiElementTypeFromAnnotation(to));
        handleStubMethodCalls(toTree);
        pruneStubChildren(toTree);
        built = true;
        return this;
    }

    /**
     * Retrieving the "from" tree. This method should only be called
     *
     * @return the "from" tree
     */
    @Override
    public PsiElement getFromPsiTree() {
        assert (built);
        return fromTree;
    }

    /**
     * TODO @orenafek please comment
     * @return
     */
    @Override
    public PsiElement getToPsiTree() {
        assert (built);
        return toTree.copy();
    }

    private PsiFile getPsiTreeFromFile(String fileName) throws IOException {
        File file = new File(this.getClass().getResource(FILE_PATH + fileName).getPath());
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

    private Class<? extends PsiElement> getPsiElementTypeFromAnnotation(PsiMethod method) {

        return Arrays.stream(method.getModifierList().getAnnotations())
                .filter(a -> LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()))
                .map(a -> a.findDeclaredAttributeValue(LEONIDAS_ANNOTATION_VALUE).getText())
                .map(s -> s.replace(".class", ""))
                .map(s -> PSI_PACKAGE_PREFIX + s)
                .map(s -> {
                    try {
                        return (Class<? extends PsiElement>) Class.forName(s); //TODO there is a warning here
                    } catch (ClassNotFoundException ignore) {
                        System.out.println(ignore.getCause().toString());
                    }
                    return PsiElement.class;
                }).collect(Collectors.toList()).get(0);
    }

    //here assuming the root element to be replaced is a direct child of the method statement block
    //TODO: @orenafek, now assuming there is only one "direct son" in rootElemntType type,
    //TODO: should be changed upon adding the name to the annotations.
    private PsiElement getTreeFromRoot(PsiMethod method, Class<? extends PsiElement> rootElementType) {
        Wrapper<PsiElement> result = new Wrapper<>();
        method.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock block) {
                result.set(Arrays.stream(block.getChildren())
                        .filter(e -> iz.ofType(e, rootElementType)).
                                collect(Collectors.toList()).get(0));
            }
        });

        return result.get();
    }

    private void handleStubMethodCalls(PsiElement method) {
        method.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                if (!iz.stubMethodCall(expression)) {
                    return;
                }
                addOrderToUserData(expression, az.integer(step.firstParamterExpression(expression)));
            }
        });
    }

    private void pruneStubChildren(PsiElement method) {
        Pruning.pruneAll(method);
    }

    private PsiElement addOrderToUserData(PsiElement element, int order) {
        element.putUserData(ID, order);
        return element;
    }

    @Override
    public Class<? extends PsiElement> getRootElementType() {
        return fromRootType;
    }
}

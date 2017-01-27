package il.org.spartan.ispartanizer.plugin.leonidas;

import com.google.common.io.Files;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import il.org.spartan.ispartanizer.auxilary_layer.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    private static final String LEONIDAS_ANNOTATION_NAME = "Leonidas";//Leonidas.class.getSimpleName();
    private static final String LEONIDAS_ANNOTATION_VALUE = "value";
    private static final String LEONIDAS_ANNOTATION_ORDER = "order";
    private static final String PSI_PACKAGE_PREFIX = "com.intellij.psi.";
    private boolean built;
    private PsiElement fromTree;
    private PsiElement toTree;
    private Class<? extends PsiElement> fromRootElementType;
    private String description;

    private int defId;
    private Map<Integer, Integer> mapToDef = new HashMap<>();

    public PsiTreeTipperBuilderImpl() {
        built = false;
        defId = 0;
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
        description = Utils.getClassFromFile(root).getDocComment().getText()
                .split("\\n")[1].trim()
                .split("\\*")[1].trim();
        fromTree = buildMethodTree(root, FROM_METHOD_NAME);
        toTree = buildMethodTree(root, TO_METHOD_NAME);
        built = true;
        return this;
    }

    private PsiElement buildMethodTree(PsiFile root, String methodName) {
        PsiMethod method = getMethodFromTree(root, methodName);
        Class<? extends PsiElement> rootType = getPsiElementTypeFromAnnotationSwitch(method);
        if (methodName.equals(FROM_METHOD_NAME))
            fromRootElementType = rootType;
        PsiElement $ = getTreeFromRoot(method, rootType);
        handleStubMethodCalls($, methodName);
        pruneStubChildren($);
        return $;
    }

    /**
     * Retrieving the "from" tree. This method should only be called after
     *
     * @return the "from" tree
     * @link {@link PsiTreeTipperBuilder}.buildTipperPsiTree was called.
     */
    @Override
    public PsiElement getFromPsiTree() {
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
    public PsiElement getToPsiTree() {
        assert (built);
        return toTree.copy();
    }

    private PsiFile getPsiTreeFromFile(String fileName) throws IOException {
        File file = new File(Utils.fixSpacesProblemOnPath(this.getClass().getResource(FILE_PATH + fileName).getPath()));
        assert (file != null);
        return PsiFileFactory.getInstance(Utils.getProject()).createFileFromText(fileName,
                FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName()),
                String.join("\n", Files.readLines(file, StandardCharsets.UTF_8)));
    }

    private PsiMethod getMethodFromTree(PsiFile f, String methodName) {
        Wrapper<PsiMethod> result = new Wrapper<>();
        f.accept(new JavaRecursiveElementVisitor() {

            @Override
            public void visitMethod(PsiMethod ¢) {
                if (step.name(¢).equals(methodName))
                    result.set(¢);
            }
        });
        return result.get();
    }

    private Class<? extends PsiElement> getPsiElementTypeFromAnnotationSwitch(PsiMethod ¢) {
        Optional<String> optionalType = Arrays.stream(¢.getModifierList().getAnnotations())
                .filter(a -> LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()))
                .map(a -> a.findDeclaredAttributeValue(LEONIDAS_ANNOTATION_VALUE).getText())
                .map(s -> s.replace(".class", "")).findFirst();
        if (!optionalType.isPresent())
            return PsiElement.class;
        String typeString = optionalType.get();
        return "PsiIfStatement".equals(typeString) ? PsiIfStatement.class
                : !"PsiWhileStatement".equals(typeString) ? PsiElement.class : PsiWhileStatement.class;
    }

    private Class<? extends PsiElement> getPsiElementTypeFromAnnotation(PsiMethod m) {
        return Arrays.stream(m.getModifierList().getAnnotations())
                .filter(a -> LEONIDAS_ANNOTATION_NAME.equals(a.getQualifiedName()))
                .map(a -> a.findDeclaredAttributeValue(LEONIDAS_ANNOTATION_VALUE).getText())
                .map(s -> s.replace(".class", ""))
                .map(s -> PSI_PACKAGE_PREFIX + s)
                .map(s -> {
                    try {
                        return (Class<? extends PsiElement>) Class.forName(s); //TODO there is a warning here
                    } catch (ClassNotFoundException ignore) {
                        throw new NullPointerException();
                        //System.out.println(ignore.getCause().toString());
                    }
                    //return PsiElement.class;
                }).collect(Collectors.toList()).get(0);
    }


    //here assuming the root element to be replaced is a direct child of the method statement block
    //TODO: @orenafek, now assuming there is only one "direct son" in rootElemntType type,
    //TODO: should be changed upon adding the name to the annotations.
    private PsiElement getTreeFromRoot(PsiMethod m, Class<? extends PsiElement> rootElementType) {
        Wrapper<PsiElement> result = new Wrapper<>();
        Wrapper<Boolean> stop = new Wrapper<>(false);
        m.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement ¢) {
                super.visitElement(¢);
                if (stop.get() || !iz.ofType(¢, rootElementType))
                    return;
                result.set(¢);
                stop.set(true);
            }

            /*@Override
            public void visitCodeBlock(PsiCodeBlock block) {
                List<PsiElement> candidates = Arrays.stream(block.getChildren())
                        .filter(e -> iz.ofType(e, rootElementType)).
                                collect(Collectors.toList());
                result.set(!candidates.isEmpty() ? candidates.get(0) : null);
            }*/
        });

        return result.get();
    }

    private void handleStubMethodCalls(PsiElement innerTree, String outerMethodName) {
        innerTree.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression x) {
                if (!iz.stubMethodCall(x))
                    return;
                Integer id;
                if (!outerMethodName.equals(FROM_METHOD_NAME))
                    id = step.firstParamterExpression(x) == null ? defId++
                            : mapToDef.get(az.integer(step.firstParamterExpression(x)));
                else {
                    if (step.firstParamterExpression(x) != null)
                        mapToDef.put(az.integer(step.firstParamterExpression(x)), defId);
                    id = defId++;
                }
                addOrderToUserData(x, id);
            }
        });
    }

    private void pruneStubChildren(PsiElement innerTree) {
        Pruning.prune(innerTree);
    }

    private PsiElement addOrderToUserData(PsiElement e, int order) {
        e.putUserData(ID, order);
        return e;
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

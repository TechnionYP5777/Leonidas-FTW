package il.org.spartan.ispartanizer.plugin.leonidas;

import com.google.common.io.Files;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import il.org.spartan.ispartanizer.auxilary_layer.Wrapper;
import il.org.spartan.ispartanizer.auxilary_layer.step;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Oren Afek
 * @since 06/01/17
 */
public class PsiTreeTipperBuilderImpl implements PsiTreeTipperBuilder {

    private static final String FILE_NAME = "RemoveCurlyBracesFromIfStatement" + ".java";
    private static final String FILE_PATH = "/spartanizer/LeonidasTippers/";
    private static final String FROM_METHOD_NAME = "from";
    private static final String TO_METHOD_NAME = "to";
    private static final String LEONIDAS_ANNOTAION_NAME = Leonidas.class.getCanonicalName();
    private static final String LEONIDAS_ANNOTATION_VALUE = "value";
    private boolean built;
    private PsiElement fromTree;
    private PsiElement toTree;

    PsiTreeTipperBuilderImpl() {
        built = false;
    }

    public PsiTreeTipperBuilderImpl buildTipperPsiTree(String fileName, Project project) throws IOException {
        assert (!built);
        PsiFile root = getPsiTreeFromFile(fileName, project);
        PsiMethod from = getMethodFromTree(root, FROM_METHOD_NAME);
        PsiMethod to = getMethodFromTree(root, TO_METHOD_NAME);
        fromTree = getTreeFromRoot(from, getPsiElementTypeFromAnnotation(from));
        toTree = getTreeFromRoot(to, getPsiElementTypeFromAnnotation(to));
        built = true;
        return this;
    }

    public PsiElement getFromPsiTree() {
        assert (built);
        return fromTree;
    }

    @Override
    public PsiElement getToPsiTree() {
        assert (built);
        return toTree;
    }

    private PsiFile getPsiTreeFromFile(String fileName, Project project) throws IOException {
        File file = new File(this.getClass().getResource(FILE_PATH + fileName).getPath());
        return PsiFileFactory.getInstance(project).createFileFromText(fileName,
                FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName()),
                String.join("\n", Files.readLines(file, StandardCharsets.UTF_8)));
    }

    private PsiMethod getMethodFromTree(PsiFile file, String methodName) {
        Wrapper<PsiMethod> result = new Wrapper<>();
        file.accept(new JavaElementVisitor() {
            @Override
            public void visitMethod(PsiMethod method) {
                if (step.name(method).equals(methodName)) {
                    result.set(method);
                }
            }
        });
        return result.get();
    }

    private Class<?> getPsiElementTypeFromAnnotation(PsiMethod method) {
        return Arrays.stream(method.getModifierList().getAnnotations())
                .filter(a -> LEONIDAS_ANNOTAION_NAME.equals(a.getQualifiedName()))
                .map(a -> a.findDeclaredAttributeValue(LEONIDAS_ANNOTATION_VALUE).getText())
                .map(s -> s.replace(".class", ""))
                .map(s -> {
                    try {
                        return Class.forName(s);
                    } catch (ClassNotFoundException ignore) {
                    }
                    return PsiElement.class;
                }).collect(Collectors.toList()).get(0);
    }

    //here assuming the root element to be replaced is a direct child of the method statemnt block
    //TODO: @orenafek, now assuming there is only one "direct son" in rootElemntType type,
    //TODO: should be changed upon adding the name to the annotations.
    private PsiElement getTreeFromRoot(PsiMethod method, Class<?> rootElementType) {
        Wrapper<PsiElement> result = new Wrapper<>();
        method.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock block) {
                result.set(Arrays.stream(block.getChildren())
                        .filter(e -> e.getClass() == rootElementType).
                                collect(Collectors.toList()).get(0));
            }
        });

        return result.get();
    }

}

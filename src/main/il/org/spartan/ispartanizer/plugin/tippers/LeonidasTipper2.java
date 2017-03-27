package il.org.spartan.ispartanizer.plugin.tippers;

import com.google.common.io.Files;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import il.org.spartan.ispartanizer.auxilary_layer.*;
import il.org.spartan.ispartanizer.plugin.EncapsulatingNode;
import il.org.spartan.ispartanizer.plugin.leonidas.Matcher;
import il.org.spartan.ispartanizer.plugin.leonidas.Pruning;
import il.org.spartan.ispartanizer.plugin.leonidas.Replacer;
import il.org.spartan.ispartanizer.plugin.tippers.leonidas.LeonidasTipperDefinition;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;
import il.org.spartan.ispartanizer.plugin.tipping.Tipper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * This class represents a tipper created by the leonidas language.
 *
 * @author Amir Sagiv, Sharon Kuninin, michalcohen
 * @since 26-03-2017.
 */
public class LeonidasTipper2 implements Tipper<PsiElement> {

    private static final String LEONIDAS_ANNOTATION_NAME = "il.org.spartan.ispartanizer.plugin.leonidas.Leonidas";
    private static final String SHORT_LEONIDAS_ANNOTATION_NAME = "Leonidas";
    private static final String LEONIDAS_ANNOTATION_VALUE = "value";


    String description;
    Matcher matcher;

    Replacer replacer;
    Class<? extends PsiElement> rootType;
    File file;
    PsiFile root;

    @SuppressWarnings("ConstantConditions")
    public LeonidasTipper2(File f, LeonidasTipperDefinition tipperDefinition) throws IOException {
        file = f;
        root = getPsiTreeFromFile(f);
        description = Utils.getClassFromFile(root).getDocComment().getText()
                .split("\\n")[1].trim()
                .split("\\*")[1].trim();
        matcher = tipperDefinition.matcherBuilder().initialzeSourceCode(root).build();
        replacer = tipperDefinition.replacer();
        rootType = getPsiElementTypeFromAnnotation(getSuperParam(0));
    }

    @Override
    public boolean canTip(PsiElement e) {
        return matcher.match(EncapsulatingNode.buildTreeFromPsi(e));
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
                replacer.replace(node, getFromTree(), getToTree(), r);
            }
        };
    }

    /**
     * @param i - The index of the generic element in the template tree.
     * @return the i'th parameter to the super constructor all when the user creates new LeonidasTipperDefinition.
     * the first parameter is the matcherBuilder and the second one is the replacer.
     * the structure of the class is:
     * class ~TipperName~ extends LeonidasTipperDefinition{
     * public ~TipperName~() {
     * super(new MatcherBuilder() {
     * '@'Override
     * '@'Leonidas(~PsiRootElementType~.class) protected void template() {
     * ~templateBody~
     * }
     * }, new Replacer() {
     * '@'Override
     * '@'Leonidas(~PsiRootElementType~.class) public void template() {
     * ~templateBody~
     * }
     * });
     * <p>
     * }
     * }
     */
    private PsiMethod getSuperParam(int i) {
        return az.newExpression(az.methodCallExpression(PsiTreeUtil.getChildOfType(root, PsiClass.class)
                .getConstructors()[0].getBody().getStatements()[0])
                .getArgumentList().getExpressions()[i])
                .getAnonymousClass()
                .getMethods()[0];
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
     * @return the generic tree representing the "to" template
     */
    private EncapsulatingNode getToTree() {
        return Pruning.prune(EncapsulatingNode.buildTreeFromPsi(getTreeFromRoot(getSuperParam(1),
                getPsiElementTypeFromAnnotation(getSuperParam(1)))));
    }

    /**
     * @return the generic tree representing the "from" template
     */
    private EncapsulatingNode getFromTree() {
        return Pruning.prune(EncapsulatingNode.buildTreeFromPsi(getTreeFromRoot(getSuperParam(0),
                getPsiElementTypeFromAnnotation(getSuperParam(0)))));
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
    private PsiFile getPsiTreeFromFile(File file) throws IOException {
        return PsiFileFactory.getInstance(Utils.getProject())
                .createFileFromText(file.getName(), FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName()),
                        String.join("\n", Files.readLines(file, StandardCharsets.UTF_8)));
    }
}

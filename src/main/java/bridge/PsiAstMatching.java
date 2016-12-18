package bridge;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.jetbrains.annotations.NotNull;
import utils.AstUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author RoeiRaz
 *
 * takes a PsiFile, makes a Psi and an AST (from jdt) out of it and creates mapping of each PsiElement to the corresponding ASTNode
 */
public class PsiAstMatching {
    private final int defaultTolerance = 1;
    private Map<PsiElement, ASTNode> nodeMap = new HashMap<>();

    public PsiAstMatching(@NotNull final PsiFile file) {
        PsiElement psiElementRoot = file.getNode().getPsi();
        ASTParser astParser = ASTParser.newParser(AST.JLS8);
        astParser.setSource(file.getText().toCharArray());
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        ASTNode astNodeRoot = astParser.createAST(null);
        connect(psiElementRoot, astNodeRoot, defaultTolerance);
        file.getNode().getPsi().accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                ASTNode match = nodeMap.get(element);
                super.visitElement(element);
            }
        });

    }

    //TODO @RoeiRaz Make sure that it makes sense and works.
    /**
     * @param psiElement
     * @param astNode
     * @return true if psiElement matches astNode and false otherwise
     */
    private boolean matching(@NotNull final PsiElement psiElement, @NotNull final ASTNode astNode) {

        if (psiElement.getTextRange().getStartOffset() != astNode.getStartPosition()) return false;
        return psiElement.getTextRange().getEndOffset() == astNode.getStartPosition() + astNode.getLength();
    }

    /**
     * creates the mapping between the psi and the AST
     *
     * @param psiElement - root of psi subtree
     * @param astNode - root of AST subtree
     * @param tolerance - not used in the meantime
     */
    private void connect(final PsiElement psiElement, final ASTNode astNode, int tolerance) {
        if (psiElement == null || astNode == null) return;
        if (matching(psiElement, astNode)) {
            nodeMap.put(psiElement, astNode);
            tolerance = defaultTolerance;
        } else if (--tolerance == 0) return;
        for (PsiElement psiChild : psiElement.getChildren()) {
            for (ASTNode astChild : AstUtils.children(astNode)) {
                connect(psiChild, astNode, tolerance);
                connect(psiElement, astChild, tolerance);
                connect(psiChild, astChild, tolerance);
            }
        }
    }

    /**
     * @return - the created mapping
     */
    public Map<PsiElement, ASTNode> getMapping() {
        return this.nodeMap;
    }

}

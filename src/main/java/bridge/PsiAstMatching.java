package bridge;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.impl.PsiBuilderAdapter;
import com.intellij.lang.impl.PsiBuilderFactoryImpl;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.psi.*;
import com.intellij.psi.impl.PsiJavaParserFacadeImpl;
import com.intellij.psi.impl.PsiManagerImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import il.org.spartan.spartanizer.engine.Recurser;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.jetbrains.annotations.NotNull;
import utils.AstUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roei on 12/9/16.
 */
public class PsiAstMatching {
    //TODO 1 @RoeiRaz write some tests

    Map<PsiElement, ASTNode> nodeMap = new HashMap<>();



    private boolean matching(@NotNull final PsiElement psiElement, @NotNull final ASTNode astNode) {
        //TODO @RoeiRaz Make sure that it makes sense and works.
        if(psiElement.getTextRange().getStartOffset() != astNode.getStartPosition()) return false;
        if(psiElement.getTextRange().getEndOffset() != astNode.getStartPosition() + astNode.getLength()) return false;
        return true;
    }

    final int defaultTolerance = 1;
    private void connect(final PsiElement psiElement, final ASTNode astNode, int tolerance) {

        //System.out.println("");
        //System.out.println("connect()");
        if(psiElement == null || astNode == null) return;
        //System.out.println("Connecting " + psiElement.getClass().getSimpleName()
        //    + " and " + astNode.getClass().getSimpleName());
        if(matching(psiElement, astNode)) {
            nodeMap.put(psiElement, astNode);
            tolerance = defaultTolerance;
            //System.out.println("MATCH!");
        }
        else if(--tolerance == 0) return;
        for(PsiElement psiChild : psiElement.getChildren()) {
            for(ASTNode astChild : AstUtils.children(astNode)) {
                connect(psiChild, astNode, tolerance);
                connect(psiElement, astChild, tolerance);
                connect(psiChild, astChild, tolerance);

            }
        }
    }

    public PsiAstMatching(@NotNull final PsiFile file) {
        PsiElement psiElementRoot = file.getNode().getPsi();
        ASTParser astParser = ASTParser.newParser(AST.JLS8);
        astParser.setSource(file.getText().toCharArray());
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        ASTNode astNodeRoot = astParser.createAST(null);
        connect(psiElementRoot, astNodeRoot, defaultTolerance);
        //System.out.println("");
        //System.out.println("");
        file.getNode().getPsi().accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {

                ASTNode match = nodeMap.get(element);
                if(match != null) {
                    //System.out.println(element.getClass().getSimpleName());
                    //System.out.println(match.getClass().getSimpleName());
                    //System.out.println("");
                }

                super.visitElement(element);
            }
        });

    }

    public Map<PsiElement, ASTNode> getMapping() {
        return this.nodeMap;
    }

}

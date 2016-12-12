package bridge;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.TreeGenerator;
import il.org.spartan.azzert;
import il.org.spartan.spartanizer.ast.safety.az;
import org.apache.commons.lang.mutable.MutableInt;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Created by roei on 12/9/16.
 */
public class Translator {

    static private ASTNode parse(String snippet, int kind) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(snippet.toCharArray());
        parser.setKind(kind);
        return parser.createAST(null);
    }

    static private int type (PsiElement element) {
        final MutableInt aquiredType = new MutableInt(-1);
        element.accept(new JavaElementVisitor() {
            @Override
            public void visitJavaFile(PsiJavaFile file) {
                aquiredType.setValue(ASTParser.K_COMPILATION_UNIT);
                super.visitJavaFile(file);
            }

            @Override
            public void visitClass(PsiClass aClass) {
                aquiredType.setValue(ASTParser.K_COMPILATION_UNIT);
                super.visitClass(aClass);
            }

            @Override
            public void visitMethod(PsiMethod method) {
                aquiredType.setValue(ASTParser.K_CLASS_BODY_DECLARATIONS);
                super.visitMethod(method);
            }

            @Override
            public void visitAnonymousClass(PsiAnonymousClass aClass) {
                aquiredType.setValue(ASTParser.K_STATEMENTS);
                super.visitAnonymousClass(aClass);
            }
        });
        return aquiredType.intValue();
    }

    static public ASTNode translate(PsiElement element) {
        azzert.fail("Failed to translate the given PsiElement: Couldn't determine explicit type.");
        return null;
    }

    static public CompilationUnit translate(PsiJavaFile psiJavaFile) {
        return az.compilationUnit(parse(psiJavaFile.getText(), ASTParser.K_COMPILATION_UNIT));
    }

}

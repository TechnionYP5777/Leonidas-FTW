package utils;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roei on 12/9/16.
 */
public class AstUtils {
    /**
     * Compensating for the bugged 'Recurser' class.
     */
    public static List<ASTNode> children(@NotNull ASTNode astNode) {

        List<ASTNode> children = new ArrayList<>();
        List<StructuralPropertyDescriptor> list = astNode.structuralPropertiesForType();

        astNode.accept(new ASTVisitor() {
            @Override
            public boolean preVisit2(ASTNode node) {
                if(node == astNode) {
                    return true;
                }
                if(node.getParent() == astNode) {
                    children.add(node);
                    return true;
                }
                return false;
            }
        });
        return children;
    }
}

package utils;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RoeiRaz
 *
 * holds utilities methods for jdt AST
 */
public class AstUtils {
    // TODO @RoeiRaz test it, or check for alternatives in il.org.spartan

    /**
     * @param astNode
     * @return a list of astNode's direct children
     */
    public static List<ASTNode> children(@NotNull ASTNode astNode) {

        List<ASTNode> children = new ArrayList<>();

        astNode.accept(new ASTVisitor() {
            @Override
            public boolean preVisit2(ASTNode node) {
                if (node == astNode) {
                    return true;
                }
                if (node.getParent() == astNode) {
                    children.add(node);
                    return true;
                }
                return false;
            }
        });
        return children;
    }
}

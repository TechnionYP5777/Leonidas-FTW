package bridge;

import utils.Wrap;
import il.org.spartan.Wrapper;
import il.org.spartan.spartanizer.ast.navigate.findFirst;
import il.org.spartan.spartanizer.ast.safety.az;
import il.org.spartan.spartanizer.ast.safety.iz;
import il.org.spartan.spartanizer.cmdline.GuessedContext;
import il.org.spartan.spartanizer.research.TipperFactory;
import il.org.spartan.spartanizer.tipping.Tipper;
import il.org.spartan.spartanizer.tipping.TipperFailure;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by roei on 12/6/16.
 */
public class TipperExecute {



    private static Wrap wrapCode(String ¢) {
        switch(GuessedContext.find(¢)) {
            case COMPILATION_UNIT_LOOK_ALIKE:
                return new Wrap("", "");
            case  OUTER_TYPE_LOOKALIKE:
                return new Wrap("", "");
            case METHOD_LOOKALIKE:
                return new Wrap("class X{", "}");
            case STATEMENTS_LOOK_ALIKE:
                return new Wrap("class X{int f(){", "}}");
            case EXPRESSION_LOOK_ALIKE:
                return new Wrap("class X{int f(){return ", ";}}");
            default:
                Assert.fail(¢ + " is not like anything I know...");
                return null;
        }
    }

    static ASTNode extractStatementIfOne(ASTNode ¢) {
        return iz.block(¢) && az.block(¢).statements().size() == 1?(ASTNode)az.block(¢).statements().get(0):¢;
    }

    static <N extends ASTNode> N findSecond(final Class<?> c, final ASTNode n) {
        if (n == null)
            return null;
        final Wrapper<Boolean> foundFirst = new Wrapper<>();
        foundFirst.set(Boolean.FALSE);
        final Wrapper<ASTNode> $ = new Wrapper<>();
        n.accept(new ASTVisitor() {
            @Override public boolean preVisit2(final ASTNode ¢) {
                if ($.get() != null)
                    return false;
                if (¢.getClass() != c && !c.isAssignableFrom(¢.getClass()))
                    return true;
                if (foundFirst.get().booleanValue()) {
                    $.set(¢);
                    assert $.get() == ¢;
                    return false;
                }
                foundFirst.set(Boolean.TRUE);
                return true;
            }
        });
        @SuppressWarnings("unchecked") final N $$ = (N) $.get();
        return $$;
    }

    static ASTNode extractASTNode(final String s, final CompilationUnit u) {
        switch (GuessedContext.find(s)) {
            case COMPILATION_UNIT_LOOK_ALIKE:
                return u;
            case EXPRESSION_LOOK_ALIKE:
                return findSecond(Expression.class, findFirst.methodDeclaration(u));
            case METHOD_LOOKALIKE:
                return findSecond(MethodDeclaration.class, u);
            case OUTER_TYPE_LOOKALIKE:
                return u;
            case STATEMENTS_LOOK_ALIKE:
                return extractStatementIfOne(findFirst.instanceOf(Block.class, u));
            default:
                break;
        }
        return null;
    }

    public static String execute(final Tipper t, String srcSnippet) {
        Wrap wrapper = wrapCode(srcSnippet);
        Document document = new Document(wrapper.wrap(srcSnippet));
        ASTParser parser = ASTParser.newParser(8);
        parser.setSource(document.get().toCharArray());
        CompilationUnit cu = (CompilationUnit)parser.createAST((IProgressMonitor)null);
        AST ast = cu.getAST();
        final ASTRewrite r = ASTRewrite.create(ast);
        ASTNode n = extractStatementIfOne(extractASTNode(srcSnippet, cu));
        n.accept(new ASTVisitor() {
            public void preVisit(ASTNode node) {
                if(t.canTip(node)) {
                    try {
                        t.tip(node).go(r, (TextEditGroup)null);
                    } catch (TipperFailure var3) {
                        var3.printStackTrace();
                    }
                }

            }
        });
        TextEdit edits = r.rewriteAST(document, (Map)null);

        try {
            edits.apply(document);
        } catch (BadLocationException | MalformedTreeException var10) {
            assert(false);
        }
        return wrapper.unwrap(document.get());
    }

    @Test public void a() {
        Tipper t = TipperFactory.tipper("$X1", "_", "Testing");
        System.out.println(execute(t, "1 + 2 + x"));
    }

    @Test public void b() {
        String pattern = "if($X1) {return $X2;} else {return $X3;}";
        String dest = "return $X1?$X2:$X3;";
        Tipper t = TipperFactory.tipper(pattern, dest, "Testing");
        System.out.println(execute(t, "if(true) {return 1;} else {return 2;}"));
    }
}

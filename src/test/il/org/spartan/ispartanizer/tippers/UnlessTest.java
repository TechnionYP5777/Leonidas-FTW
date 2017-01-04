package il.org.spartan.ispartanizer.tippers;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.PsiTestCase;
import il.org.spartan.ispartanizer.auxilary_layer.Wrapper;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.tippers.Unless;
import org.junit.Test;

/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class UnlessTest extends TipperTest {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testcanTipThen() throws Exception {
        PsiFile f = createDummyFile("banana.java", "class A{ int foo(int x) { return x > 0 ? null : x; } }");
        PsiElement e = f.getNode().getPsi();
        final Wrapper<PsiConditionalExpression> w = new Wrapper<>();
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitConditionalExpression(PsiConditionalExpression expression) {
                super.visitConditionalExpression(expression);
                w.set(expression);
            }
        });
        assert (iz.conditionalExpression(w.get()));
        assert (iz.nullExpression(w.get().getThenExpression()));
        assert ((new Unless()).canTip(w.get()));
    }

    @Test
    public void testcantTip() throws Exception {
        PsiFile f = createDummyFile("banana.java", "class A{ int foo(int x, int _null) { return x > 0 ? _null : x; } }");
        PsiElement e = f.getNode().getPsi();
        final Wrapper<PsiConditionalExpression> w = new Wrapper<>();
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitConditionalExpression(PsiConditionalExpression expression) {
                super.visitConditionalExpression(expression);
                w.set(expression);
            }
        });
        assert (iz.conditionalExpression(w.get()));
        assert (!iz.nullExpression(w.get().getThenExpression()));
        assert (!(new Unless()).canTip(w.get()));
    }

    private void printPsi(PsiElement e) {
        Wrapper<Integer> tabs = new Wrapper<>(0);
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                for (int i = 0; i < tabs.get(); i++) {
                    System.out.print("\t");
                }
                System.out.println(element);
                tabs.set(tabs.get() + 1);
                super.visitElement(element);

            }
        });
    }

}
package il.org.spartan.ispartanizer.plugin.leonidas;

import com.intellij.psi.*;
import il.org.spartan.ispartanizer.auxilary_layer.Wrapper;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.tippers.TipperTest;

import java.util.Map;

/**
 * @author AnnaBel7
 * @since 09/01/2017.
 */
public class ReplacerTest extends TipperTest {
    public void testReplace1() {

    }

    public void testReplace2() {

    }

    public void testReplace3() {

    }

    public void testExtractInfo1() {
        PsiIfStatement b = createTestIfStatement("booleanExpression()", "statement();");
        Wrapper<Integer> count = new Wrapper<>(0);
        b.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitCodeBlock(PsiCodeBlock block) {
                super.visitCodeBlock(block);
                block.putUserData(KeyDescriptionParameters.NO_OF_STATEMENTS, Amount.EXACTLY_ONE);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression methodCallExpression) {
                super.visitMethodCallExpression(methodCallExpression);
                methodCallExpression.putUserData(KeyDescriptionParameters.GENERIC_NAME, methodCallExpression.getMethodExpression().getText());
                methodCallExpression.putUserData(KeyDescriptionParameters.ID, count.get());

                count.set(count.get() + 1);
            }
        });

        Pruning.pruneAll(b);

        PsiIfStatement y = createTestIfStatement("true", " int y = 5; ");
        assertTrue(PsiTreeMatcher.match(b, y));
        Map<Integer, PsiElement> m = Replacer.extractInfo(b, y);
        assertTrue(iz.expression(m.get(0)));
        assertTrue(iz.statement(m.get(1)));
    }

}
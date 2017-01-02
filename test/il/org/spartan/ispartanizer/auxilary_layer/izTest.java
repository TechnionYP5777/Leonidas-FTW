package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import com.intellij.testFramework.PsiTestCase;


/**
 * @author michal cohen
 * @since 12/22/2016.
 */
public class izTest extends PsiTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testblock() throws Exception {

    }

    public void testmethodCallExpression() throws Exception {

    }

    public void testdeclarationStatement() throws Exception {

    }

    public void testenumConstant() throws Exception {

    }

    public void testfieldDeclaration() throws Exception {

    }

    public void testabstract$() throws Exception {

    }

    public void teststatic$() throws Exception {

    }

    public void testsingleParameteredMethod() throws Exception {

    }

    public void testvoid$() throws Exception {

    }

    public void testpublic$() throws Exception {

    }

    public void testmain() throws Exception {

    }

    public void testreturnStatement() throws Exception {

    }

    public void testtype() throws Exception {

    }

    public void testmethodInvocation() throws Exception {

    }

    public void testexpressionStatement() throws Exception {

    }

    public void testidentifier() throws Exception {

    }

    public void testconditionalExpression() throws Exception {

    }

    public void testnullExpression() throws Exception {
        PsiFile f = createDummyFile("banana.java", "class A{ int foo(int x) { return x > 0 ? null : x; } }");
        PsiElement e = f.getNode().getPsi();
        final Wrapper<PsiConditionalExpression> w = new Wrapper<>();
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitConditionalExpression(PsiConditionalExpression expression) {
                super.visitConditionalExpression(expression);
                w.inner = expression;
            }
        });
        assert (iz.conditionalExpression(w.inner));
        assert (iz.nullExpression(w.inner.getThenExpression()));
    }
}
package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;

/**
 * @author michalcohen
 * @since 31-12-16
 */
public class type {
    public static Class<? extends PsiElement> of(PsiElement x) {
        Wrapper<Class<? extends PsiElement>> myClass = new Wrapper<>(PsiElement.class);
        x.accept(new JavaElementVisitor() {
            @Override
            public void visitCallExpression(PsiCallExpression callExpression) {
                super.visitCallExpression(callExpression);
                myClass.set(PsiCallExpression.class);
            }

            @Override
            public void visitMethodReferenceExpression(PsiMethodReferenceExpression expression) {
                super.visitMethodReferenceExpression(expression);
                myClass.set(PsiMethodReferenceExpression.class);
            }

            @Override
            public void visitRequiresStatement(PsiRequiresStatement statement) {
                super.visitRequiresStatement(statement);
                myClass.set(PsiRequiresStatement.class);
            }

            @Override
            public void visitMethod(PsiMethod statement) {
                super.visitMethod(statement);
                myClass.set(PsiMethod.class);
            }

            @Override
            public void visitConditionalExpression(PsiConditionalExpression statement) {
                super.visitConditionalExpression(statement);
                myClass.set(PsiConditionalExpression.class);
            }

            @Override
            public void visitIdentifier(PsiIdentifier statement) {
                super.visitIdentifier(statement);
                myClass.set(PsiIdentifier.class);
            }

            @Override
            public void visitForeachStatement(PsiForeachStatement statement) {
                super.visitForeachStatement(statement);
                myClass.set(PsiForeachStatement.class);
            }

            @Override
            public void visitLambdaExpression(PsiLambdaExpression statement) {
                super.visitLambdaExpression(statement);
                myClass.set(PsiLambdaExpression.class);
            }

            @Override
            public void visitExpression(PsiExpression statement) {
                super.visitExpression(statement);
                myClass.set(PsiExpression.class);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression statement) {
                super.visitMethodCallExpression(statement);
                myClass.set(PsiMethodCallExpression.class);
            }
        });
        return myClass.get();
    }

}

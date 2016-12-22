package auxilary_layer;

import com.intellij.psi.*;

import java.util.Arrays;


/**
 * @author Michal Cohen
 * @since 2016.12.1
 */
public enum haz {
    ;
    public static boolean variableDefenition(final PsiElement p){
        final Wrapper<Boolean> b = new Wrapper<>(Boolean.FALSE);
        p.accept(new PsiRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement e) {
                if(iz.declarationStatement(e)){
                    PsiDeclarationStatement d = az.declarationStatement(e);
                    super.visitElement(d);
                    b.inner = d.getDeclaredElements().length > 0;
                } else if(iz.enumConstant(e)){
                    PsiEnumConstant d = az.enumConstant(e);
                    super.visitElement(d);
                    b.inner = Boolean.TRUE;
                } else if (iz.fieldDeclaration(e)) {
                    PsiField d = az.fieldDeclaration(e);
                    super.visitElement(d);
                    b.inner = Boolean.TRUE;
                }

            }
        });
        return b.inner;
    }


    public static boolean centVariableDefenition(final PsiElement p){
        final Wrapper<Boolean> b = new Wrapper<>(Boolean.FALSE);
        p.accept(new PsiRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement e) {
                if(iz.declarationStatement(e)){
                    PsiDeclarationStatement d = az.declarationStatement(e);
                    super.visitElement(d);
                    b.inner = Arrays.stream(d.getDeclaredElements()).anyMatch(z -> z.textContains('¢'));
                } else if(iz.enumConstant(e)){
                    PsiEnumConstant d = az.enumConstant(e);
                    super.visitElement(d);
                    b.inner = d.getName().equals("¢");
                } else if (iz.fieldDeclaration(e)) {
                    PsiField d = az.fieldDeclaration(e);
                    super.visitElement(d);
                    b.inner = d.getName().equals("¢");
                }
            }
        });
        return b.inner;
    }

    public static boolean functionNamed(final PsiElement p, String name) {
        final Wrapper<Boolean> b = new Wrapper<>(Boolean.FALSE);
        p.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitMethod(PsiMethod method) {
                super.visitMethod(method);
                b.inner = method.getName().equals(name) ? Boolean.TRUE : b.inner;
            }
        });
        return b.inner;
    }



}

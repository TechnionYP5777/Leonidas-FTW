package auxilary_layer;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Oren Afek
 * @since 2016.12.1
 */

public enum Utils {
    ;

    public static <T> boolean in(T candidate, T... list) {
        return Arrays.stream(list).anyMatch(elem -> elem.equals(candidate));
    }

    public static PsiManager getPsiManager(Project project) {
        return PsiManager.getInstance(project);
    }

    public static PsiClass findClass(PsiElement element) {
        if (element instanceof PsiClass) {
            return (PsiClass) element;
        }

        if (element.getParent() != null) {
            return findClass(element.getParent());
        }

        return null;
    }

    public static PsiMethod findMethodByName(PsiClass clazz, String name) {
        Arrays.stream(clazz.getMethods());
        PsiMethod[] methods = clazz.getMethods();

        // use reverse to find from botton as the duplicate conflict resolution policy requires this
        for (int i = methods.length - 1; i >= 0; i--) {
            PsiMethod method = methods[i];
            if (name.equals(method.getName()))
                return method;
        }
        return null;
    }

    public static PsiClass getCurrentClass(PsiJavaFile javaFile, Editor editor) {
        if (javaFile == null) {
            return null;
        }
        PsiElement element = javaFile.findElementAt(editor.getCaretModel().getOffset());
        return element != null ? findClass(element) : null;
    }

    public static boolean conforms(Class<?> from, Class<?> to) {
        if (from == PsiMethod.class) {

        }
        return from != null && (from == to ||
                conforms(from.getSuperclass(), to) || (from.getInterfaces() != null &&
                Arrays.stream(from.getInterfaces()).anyMatch(i -> conforms(i, to))));
    }

    public static List<PsiIdentifier> getAllReferences(PsiElement root, PsiIdentifier i) {
        List<PsiIdentifier> identifiers = new ArrayList<>();

        root.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitIdentifier(PsiIdentifier identifier) {
                super.visitIdentifier(identifier);
                if (identifier.getText().equals(i.getText())) {
                    identifiers.add(identifier);
                }
            }
        });
        return identifiers;
    }

    public static PsiClass getContainingClass(PsiElement e) {
        return iz.classDeclaration(e.getParent()) ? az.classDeclaration(e.getParent()) : getContainingClass(e.getParent());
    }

    public static PsiImportList getImportList(PsiFile f){
        return az.importList(PsiTreeUtil.getChildOfType(f, PsiImportList.class));
    }

    public static PsiClass getClassFromFile(PsiFile f){
        return az.classDeclaration(f.getChildren()[4]);
    }

}

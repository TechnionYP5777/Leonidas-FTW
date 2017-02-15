package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * General utils class
 * @author 01-12-2016
 */
public enum Utils {
    ;

    public static <T> boolean in(T candidate, T... list) {
        return list!=null && Arrays.stream(list).anyMatch(elem -> elem.equals(candidate));
    }

    public static PsiManager getPsiManager(Project project) {
        return PsiManager.getInstance(project);
    }

    public static PsiClass findClass(PsiElement element) {

        if(element == null){
            return null;
        }

        if (element instanceof PsiClass) {
            return (PsiClass) element;
        }

        if (element.getParent() != null) {
            return findClass(element.getParent());
        }

        return null;
    }

    public static PsiMethod findMethodByName(PsiClass clazz, String name) {
        if(clazz == null){
            return null;
        }

        Arrays.stream(clazz.getMethods());
        PsiMethod[] methods = clazz.getMethods();
        // use reverse to find from bottom as the duplicate conflict resolution policy requires this
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
        return from != null && (from == to ||
                conforms(from.getSuperclass(), to) || (from.getInterfaces() != null &&
                Arrays.stream(from.getInterfaces()).anyMatch(i -> conforms(i, to))));
    }

    public static List<PsiIdentifier> getAllReferences(PsiElement root, PsiIdentifier i) {
        List<PsiIdentifier> identifiers = new ArrayList<>();
        if(root == null || i == null){
            return identifiers;
        }
        root.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitIdentifier(PsiIdentifier identifier) {
                super.visitIdentifier(identifier);
                if (identifier.getText().equals(i.getText())) {
                    PsiElement context = identifier.getContext();
                    if(iz.variable(context) || iz.referenceExpression(context))
                        identifiers.add(identifier);
                }
            }
        });
        return identifiers;
    }

    public static PsiClass getContainingClass(PsiElement e) {
        return iz.classDeclaration(e.getParent()) ? az.classDeclaration(e.getParent()) : getContainingClass(e.getParent());
    }

    public static PsiImportList getImportList(PsiFile f) {
        return az.importList(PsiTreeUtil.getChildOfType(f, PsiImportList.class));
    }

    public static PsiClass getClassFromFile(PsiFile f) {
        return az.classDeclaration(f.getChildren()[4]);
    }

    public static Document getDocumentFromPsiElement(PsiElement e) {
        PsiFile associatedFile = e.getContainingFile();
        return PsiDocumentManager.getInstance(associatedFile.getProject()).getDocument(associatedFile);
    }

    public static Project getProject() {
        return ProjectManager.getInstance().getOpenProjects()[0];
    }

    private static String showPsiTreeAux(PsiElement e, int indent) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            s.append("\t");
        }
        s.append(e.getClass().getName() + ": " + e.getText() + "\n");
        for (PsiElement child : e.getChildren()) {
            s.append(showPsiTreeAux(child, indent + 1));
        }
        return s.toString();
    }

    public static String showPsiTree(PsiElement e) {
        return showPsiTreeAux(e, 0);
    }

    public static <T extends PsiElement> List<T> getChildrenOfType(@Nullable PsiElement e, @NotNull Class<T> aClass) {
        Wrapper<List<T>> w = new Wrapper<>(new LinkedList<T>());
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                if (aClass.isInstance(element)) {
                    w.get().add((T) element);
                }
            }
        });
        return w.get();
    }

    /**
     * fixed problems on machines where the project path has spaces in it:
     * the .getPath() of getResource inserts %20 instead of spaces
     * @param path the path to be fixed
     * @return fixed path. on error, returns null
     */
    public static String fixSpacesProblemOnPath(String path){
        String fixedPath = null;
        try {
            fixedPath = URLDecoder.decode(path, "UTF-8");
        } catch(UnsupportedEncodingException u){

        }
        return fixedPath;
    }
}

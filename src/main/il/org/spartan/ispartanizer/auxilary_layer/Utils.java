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

    public static PsiManager getPsiManager(Project ¢) {
        return PsiManager.getInstance(¢);
    }

    public static PsiClass findClass(PsiElement ¢) {
        return ¢ == null ? null
                : ¢ instanceof PsiClass ? (PsiClass) ¢ : ¢.getParent() == null ? null : findClass(¢.getParent());
    }

    public static PsiMethod findMethodByName(PsiClass clazz, String name) {
        if (clazz == null)
            return null;

        Arrays.stream(clazz.getMethods());
        PsiMethod[] methods = clazz.getMethods();
        // use reverse to find from bottom as the duplicate conflict resolution policy requires this
        for (int ¢ = methods.length - 1; ¢ >= 0; --¢)
            if (name.equals(methods[¢].getName()))
                return methods[¢];
        return null;
    }

    public static PsiClass getCurrentClass(PsiJavaFile f, Editor e) {
        if (f == null)
            return null;
        PsiElement element = f.findElementAt(e.getCaretModel().getOffset());
        return element == null ? null : findClass(element);
    }

    public static boolean conforms(Class<?> from, Class<?> to) {
        return from != null && (from == to ||
                conforms(from.getSuperclass(), to) || (from.getInterfaces() != null &&
                Arrays.stream(from.getInterfaces()).anyMatch(i -> conforms(i, to))));
    }

    public static List<PsiIdentifier> getAllReferences(PsiElement root, PsiIdentifier i) {
        List<PsiIdentifier> $ = new ArrayList<>();
        if (root == null || i == null)
            return $;
        root.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitIdentifier(PsiIdentifier id) {
                super.visitIdentifier(id);
                if (!id.getText().equals(i.getText()))
                    return;
                PsiElement context = id.getContext();
                if (iz.variable(context) || iz.referenceExpression(context))
                    $.add(id);
            }
        });
        return $;
    }

    public static PsiClass getContainingClass(PsiElement ¢) {
        return iz.classDeclaration(¢.getParent()) ? az.classDeclaration(¢.getParent()) : getContainingClass(¢.getParent());
    }

    public static PsiImportList getImportList(PsiFile ¢) {
        return az.importList(PsiTreeUtil.getChildOfType(¢, PsiImportList.class));
    }

    public static PsiClass getClassFromFile(PsiFile ¢) {
        return az.classDeclaration(¢.getChildren()[4]);
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
        for (int ¢ = 0; ¢ < indent; ++¢)
            s.append("\t");
        s.append(e.getClass().getName() + ": " + e.getText() + "\n");
        for (PsiElement child : e.getChildren())
            s.append(showPsiTreeAux(child, indent + 1));
        return s + "";
    }

    public static String showPsiTree(PsiElement ¢) {
        return showPsiTreeAux(¢, 0);
    }

    public static <T extends PsiElement> List<T> getChildrenOfType(@Nullable PsiElement e, @NotNull Class<T> aClass) {
        Wrapper<List<T>> w = new Wrapper<>(new LinkedList<T>());
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement ¢) {
                super.visitElement(¢);
                if (aClass.isInstance(¢))
                    w.get().add((T) ¢);
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
        String $ = null;
        try {
            $ = URLDecoder.decode(path, "UTF-8");
        } catch(UnsupportedEncodingException u){

        }
        return $;
    }
}

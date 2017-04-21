package il.org.spartan.Leonidas.auxilary_layer;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import il.org.spartan.Leonidas.plugin.tippers.leonidas.RemoveCurlyBracesFromIfStatement;
import il.org.spartan.Leonidas.plugin.utils.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * General utils class
 *
 * @author 01-12-2016
 */
public enum Utils {
    ;
    public static final String SUFFIX = ".java";
    static Logger logger = new Logger(Utils.class);

    @SafeVarargs
    public static <T> boolean in(T candidate, T... list) {
        return list != null && Arrays.stream(list).anyMatch(elem -> elem.equals(candidate));
    }

    public static PsiManager getPsiManager(Project p) {
        return PsiManager.getInstance(p);
    }

    public static PsiClass findClass(PsiElement e) {
		return e == null ? null
				: e instanceof PsiClass ? (PsiClass) e : e.getParent() == null ? null : findClass(e.getParent());
	}

    public static PsiMethod findMethodByName(PsiClass clazz, String name) {
        if (clazz == null)
			return null;
		Arrays.stream(clazz.getMethods());
		PsiMethod[] methods = clazz.getMethods();
		for (int i = methods.length - 1; i >= 0; --i)
			if (name.equals(methods[i].getName()))
				return methods[i];
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
        List<PsiIdentifier> identifiers = new ArrayList<>();
        if (root != null && i != null)
			root.accept(new JavaRecursiveElementVisitor() {
				@Override
				public void visitIdentifier(PsiIdentifier i) {
					super.visitIdentifier(i);
					if (!i.getText().equals(i.getText()))
						return;
					PsiElement context = i.getContext();
					if (iz.variable(context) || iz.referenceExpression(context))
						identifiers.add(i);
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

    public static PsiClass getClassFromFile(PsiJavaFile f) {
        return f.getClasses()[0];
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
        for (int i = 0; i < indent; ++i)
			s.append("\t");
        s.append(e.getClass().getName()).append(": ").append(e.getText()).append("\n");
        for (PsiElement child : e.getChildren())
			s.append(showPsiTreeAux(child, indent + 1));
        return s.toString();
    }

    public static String showPsiTree(PsiElement e) {
        return showPsiTreeAux(e, 0);
    }

    public static <T extends PsiElement> List<T> getChildrenOfType(@Nullable PsiElement e, @NotNull Class<T> aClass) {
        Wrapper<List<T>> w = new Wrapper<>(new LinkedList<T>());
        assert e != null;
        e.accept(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement e) {
                super.visitElement(e);
                if (aClass.isInstance(e))
					w.get().add((T) e);
            }
        });
        return w.get();
    }

    public static void main(String[] args) {
        try {
            System.out.println(getSourceCode(RemoveCurlyBracesFromIfStatement.class));
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public static String getSourceCode(Class<?> c) throws IOException {

        try (InputStream is = c.getClassLoader().getResourceAsStream(c.getName().replaceAll("\\.", "/") + ".java")) {
            return IOUtils.toString(new BufferedReader(new InputStreamReader(is)));
        } catch (IOException e) {
            logger.error("", e);
        }

        return "";
    }

    /**
     * Returns path to... // TODO sharon
     *
     * @param c class to get path to
     * @return path to the class
     */
    public static String pathToClass(Class<?> c) {
        String FS = System.getProperty("file.separator");
        ClassLoader loader = Utils.class.getClassLoader();

        String currDir = loader.getResource(c.getName().replaceAll("\\.", "/") + ".class").toString()
				.replaceAll("/", Matcher.quoteReplacement(FS)).replaceAll("\\\\", Matcher.quoteReplacement(FS));
        return (!currDir.startsWith("file:\\") ? currDir : currDir.substring("file:\\".length())).replaceAll("\\.class",
				".java");
    }

    /**
     * fixed problems on machines where the project path has spaces in it:
     * the .getPath() of getResource inserts %20 instead of spaces
     *
     * @param path the path to be fixed
     * @return fixed path. on error, returns null
     */
    public static String fixSpacesProblemOnPath(String path) {
        String fixedPath = null;
        try {
            fixedPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
        }
        return fixedPath;
    }

    public static <T> String getListText(List<T> l) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        l.stream().forEach(a -> sb.append(a.toString() + ", "));
        sb.append("}");
        return sb.toString();
    }

    public static <T> String getArrayText(T[] l) {
        return getListText(Arrays.asList(l));
    }
}

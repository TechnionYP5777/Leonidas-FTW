package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiElement;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;


/**
 * Created by maorroey on 1/6/2017.
 */
public class CompilationCenter {
    /*
        A static util class that handles compilation inside the code
     */

    static final CompilerManager compilerManager = CompilerManager.getInstance(ProjectManager.getInstance().getDefaultProject());

    static boolean hasCompilationErrors(PsiElement element){
            if(false) { //skeleton only
                final Module module = ModuleUtil.findModuleForPsiElement(element);
                assert (module != null);
                compilerManager.compile(module, new psiElementCompilerStatusNotification());
                return false;
            }
            return false;

    }

    private static class psiElementCompilerStatusNotification implements CompileStatusNotification {
        public void finished(boolean aborted, int errors, int warnings, final CompileContext compileContext) {
            System.out.println("number of compilation errors: "+errors);
        }
    }
}

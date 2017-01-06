package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.openapi.compiler.CompileContext;
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
    static boolean hasCompilationErrors(PsiElement element){
        return false;
    }

    public static void finishedCompilationCallback(boolean aborted, int errors, int warnings, final CompileContext compileContext){

    }
}

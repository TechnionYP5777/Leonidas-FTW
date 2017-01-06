package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.module.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


/**
 * Created by maorroey on 1/6/2017.
 */
public class CompilationCenter {
    /*
        A static util class that handles compilation inside the code
     */
    private static File dummyCompilationTestFile;
    private static JavaCompiler compiler;

    public static void initialize(){
        File root = new File("/dummyJavaFile"); // On Windows running on C:\, this is C:\java.
        dummyCompilationTestFile = new File(root, "compilationTest/Test.java");
        dummyCompilationTestFile.getParentFile().mkdirs();
        compiler = ToolProvider.getSystemJavaCompiler();
    }

    static boolean hasCompilationErrors(PsiFile file){
        String source = file.getText();
        try {
            Files.write(dummyCompilationTestFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compiler.run(null, null, baos, dummyCompilationTestFile.getPath());
        String resultingErrorString = baos.toString();
        return resultingErrorString.length() != 0;
    }


}

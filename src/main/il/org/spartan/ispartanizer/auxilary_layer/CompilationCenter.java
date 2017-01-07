package il.org.spartan.ispartanizer.auxilary_layer;

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
    private static boolean initialized = false;
    private static ByteArrayOutputStream output;
    private static ByteArrayOutputStream errors;

    public static void initialize(){
        if(initialized){
            return;
        }
        File root = new File("/dummyJavaFile");
        dummyCompilationTestFile = new File(root, "compilationTest/Test.java");
        dummyCompilationTestFile.getParentFile().mkdirs();
        compiler = ToolProvider.getSystemJavaCompiler();
        output = new ByteArrayOutputStream();
        errors = new ByteArrayOutputStream();
        initialized = true;
    }

    static boolean hasCompilationErrors(PsiFile file){
        compile(file);
        return errors.toString().length() != 0;
    }

    private static void compile(PsiFile file){
        if(!initialized){
            initialize();
        }
        String source = file.getText();
        try {
            Files.write(dummyCompilationTestFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        output.reset();
        errors.reset();
        compiler.run(null, output, errors, dummyCompilationTestFile.getPath());
    }


}

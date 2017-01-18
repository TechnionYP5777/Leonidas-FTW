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
 * A static util class that handles compilation inside the code
 * @author Roey Maor
 * @since 06-01-2017
 */
public class CompilationCenter {
    private static final String DUMMY_DIR_PATH = "/spartanizer/CompiledDummy/";
    private static final String DUMMY_FILE_NAME = "Test.java";
    private static File dummyCompilationTestFile;
    private static JavaCompiler compiler;
    private static boolean initialized = false;
    private static ByteArrayOutputStream output;
    private static ByteArrayOutputStream errors;

    /**
     * initialize the compilation center:
     * -dummy directory and file
     * -compiler
     * -output streams
     */
    public static void initialize(){
        if(initialized){
            return;
        }

        assert(CompilationCenter.class.getClassLoader().getResource(DUMMY_DIR_PATH+DUMMY_FILE_NAME) != null);
        dummyCompilationTestFile = new File(CompilationCenter.class.getClassLoader().getResource(DUMMY_DIR_PATH+DUMMY_FILE_NAME).getPath());
        compiler = ToolProvider.getSystemJavaCompiler();
        output = new ByteArrayOutputStream();
        errors = new ByteArrayOutputStream();
        initialized = true;
    }


    public static boolean hasCompilationErrors(PsiFile file) {
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

    /**
     * TODO @maorroey please comment
     */
    public static String getLatestCompilationErrors(){
        if(!initialized){
            initialize();
        }
        return errors.toString();
    }

    /**
     * TODO @maorroey please comment
     */
    public static String getLatestCompilationOutput(){
        if(!initialized){
            initialize();
        }
        return output.toString();
    }

}

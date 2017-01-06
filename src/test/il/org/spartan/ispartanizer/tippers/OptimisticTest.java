package il.org.spartan.ispartanizer.tippers;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.impl.ModuleImpl;
import com.intellij.openapi.project.ProjectManager;
import il.org.spartan.ispartanizer.auxilary_layer.CompilationCenter;
import il.org.spartan.ispartanizer.auxilary_layer.haz;

/**
 * Created by maorroey on 1/4/2017.
 */
public class OptimisticTest extends TipperTest {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        CompilationCenter.initialize();
    }

    public void testDetectsSyntaxErrors() throws Exception {
        assertTrue(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return (5=!=5);}")));
        //assertTrue(haz.compilationErrors(createFile(CompilationCenter.getCompilationTestModule(),"testy.java","class testy { int dummy(){final int x=5; x=3; return x;} }")));
    }

    public void testNoSyntaxErrors(){
        assertFalse(haz.syntaxErrors(createTestExpressionFromString("5 == 5")));
        assertFalse(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return false;}")));
        assertFalse(haz.syntaxErrors(createTestMethodFromString("boolean dummy(){return (5==5);}")));
    }

}

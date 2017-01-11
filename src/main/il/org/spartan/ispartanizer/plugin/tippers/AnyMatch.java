package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiForeachStatement;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;

/**
 * Created by maorroey on 12/26/2016.
 */
public class AnyMatch extends NanoPatternTipper<PsiForeachStatement> {

    /*
    follows the pattern: "for($T $N : $X1) if($X2) return true; return false;", "return $X1.stream().anyMatch($N -> $X2);"
     */

    public boolean canTip(PsiElement ¢) {
    /*
        if( !iz.forEachStatement(¢) ){
            System.out.println("=====================================================================================");
            System.out.println(¢.getText() + "  and type  " + ¢.getClass());
            System.out.println("=====================================================================================");

            return false;
        }

        PsiStatement[] statementsInBody = az.blockStatement(az.forEachStatement(¢).getBody()).getCodeBlock().getStatements();
        if(statementsInBody == null || statementsInBody.length != 2 ||
                !iz.ifStatement(statementsInBody[0]) || !iz.returnStatement(statementsInBody[1])){
            System.out.println("yeyo1");

            return false;
        }

        PsiStatement[] statementsInIf = az.blockStatement(az.forEachStatement(statementsInBody[0])).getCodeBlock().getStatements();

        if(statementsInIf == null || statementsInIf.length != 1 || !iz.returnStatement(statementsInIf[0])){
            System.out.println("yeyo2");

            return false;
        }

        if(!az.returnStatement(statementsInIf[0]).getReturnValue().getText().equals("true") ||
                !az.returnStatement(statementsInBody[1]).getReturnValue().getText().equals("false")){
            System.out.println("yeyo3");

            return false;
        }

        return true;
        */
        return false;
    }

    @Override
    public String description(PsiForeachStatement ¢) {
        return "Replaces anyMatch-for statement with the appropriate .stream().AnyMatch Java Collection function call";
    }

    @Override
    public PsiElement createReplacement(PsiForeachStatement e) {
        return null;
    }

    @Override
    protected Tip pattern(final PsiForeachStatement ¢) {
        return tip(¢);

        //PsiExpression X1 = az.forEachStatement(¢).getIteratedValue();
        //PsiIdentifier N = az.forEachStatement(¢).getIterationParameter().getNameIdentifier();
    }

    @Override
    public Class<PsiForeachStatement> getPsiClass() {
        return PsiForeachStatement.class;
    }
}

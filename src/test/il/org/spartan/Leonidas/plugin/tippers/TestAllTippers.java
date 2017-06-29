package il.org.spartan.Leonidas.plugin.tippers;
import il.org.spartan.Leonidas.PsiTypeHelper;
import il.org.spartan.Leonidas.plugin.Toolbox;
import il.org.spartan.Leonidas.plugin.tippers.leonidas.LeonidasTipperDefinition;
import il.org.spartan.Leonidas.plugin.tipping.Tipper;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.List;


/**
 * @author @roey maor
 * @since 23-06-2017.
 */
public class TestAllTippers extends PsiTypeHelper {


    @Test
    public void testTippers(){
        Toolbox t = Toolbox.getInstance();
        List<LeonidasTipperDefinition> leonidasTippers = t.getAllTipperInstances();
        List<Tipper> ot = t.getAllTippers();
        for( LeonidasTipperDefinition lt : leonidasTippers){
            Class<? extends LeonidasTipperDefinition> c = lt.getClass();
            if(!c.isAnnotationPresent(LeonidasTipperDefinition.TipperUnderConstruction.class)){
                TipperTest ts = new TipperTest(lt, this, true, true);
                ts.check();
            }

        }
        for( Tipper tipper : ot){
            TipperTest ts = new TipperTest(tipper, this, false, false);
            ts.check();
        }

    }

}

package il.org.spartan.Leonidas.plugin.tippers.leonidas;
import il.org.spartan.Leonidas.auxilary_layer.ExampleMapFactory;

import java.util.Map;

import static il.org.spartan.Leonidas.plugin.leonidas.BasicBlocks.GenericPsiElementStub.expression;


/**
 * X*1  ->  X
 *
 * @author Roey Maor
 */
public class InfixMultiplicationByOne implements LeonidasTipperDefinition{
    int identifier0;

    @Override
    public void matcher() {
        new Template(() -> /* start */ identifier0*1 /* end */);
    }

    @Override
    public void replacer() {
        new Template(() -> /* start */ identifier0 /* end */);
    }

    @Override
    public Map<String, String> getExamples() {
        return new ExampleMapFactory()
                .put("X*1", "X")
                .map();
    }
}

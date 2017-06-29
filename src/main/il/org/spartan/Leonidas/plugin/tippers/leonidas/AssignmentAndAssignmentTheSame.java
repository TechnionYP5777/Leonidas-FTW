package il.org.spartan.Leonidas.plugin.tippers.leonidas;

import il.org.spartan.Leonidas.auxilary_layer.ExampleMapFactory;

import java.util.Map;

/**
 * Inline assignment into subsequent assignment.
 *
 * @author Oren
 * @since 29-06-2017
 */
@LeonidasTipperDefinition.TipperUnderConstruction(LeonidasTipperDefinition.UnderConstructionReason.BROKEN_MATCHER)
public class AssignmentAndAssignmentTheSame implements LeonidasTipperDefinition {


    @Override
    public void constraints() {
    }

    X identifier0;

    class X {

        public X identifier1() {
            return this;
        }

        public X identifier2() {
            return this;
        }
    }

    @Override
    public void matcher() {
        new Template(() -> {
            /* start */
            identifier0 = identifier0.identifier1();
            identifier0 = identifier0.identifier2();
            /* end */
        });
    }


    @Override
    public void replacer() {
        new Template(() -> {
            /* start */
            identifier0 = identifier0.identifier1().identifier2();
            /* end */
        });
    }

    @Override
    public Map<String, String> getExamples() {
        return new ExampleMapFactory()
                .put("s = s.trim();\n s = s.substring(0,1);", "s = s.trim().substring(0,1);")
                .map();

    }
}
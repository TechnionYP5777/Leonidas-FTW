package il.org.spartan.Leonidas.plugin.leonidas.BasicBlocks;

import com.intellij.psi.PsiElement;
import il.org.spartan.Leonidas.auxilary_layer.Wrapper;
import il.org.spartan.Leonidas.auxilary_layer.az;
import il.org.spartan.Leonidas.auxilary_layer.step;
import il.org.spartan.Leonidas.plugin.leonidas.Matcher;

import java.util.List;
import java.util.Map;

/**
 * Created by melanyc on 7/1/2017.
 */
public class AllMethodCallBased extends QuantifierMethodCallBased {

    private static final String TEMPLATE = "all";

    public AllMethodCallBased(PsiElement e, Encapsulator i) {
        super(e, TEMPLATE, i);
    }

    public AllMethodCallBased() {
        super(TEMPLATE);
    }

    @Override
    public int getNumberOfOccurrences(EncapsulatorIterator i, Map<Integer, List<PsiElement>> m) {
        if (i.value().getParent() == null) return 1;
        Wrapper<Integer> count = new Wrapper<>(0);
        //noinspection StatementWithEmptyBody
        i.value().getParent().accept(n -> {
            if (generalizes(n, m).matches()) count.set(count.get() + 1);
        });
        return count.get();
    }

    @Override
    public AllMethodCallBased create(Encapsulator e, Map<Integer, List<Matcher.Constraint>> map) {
        PsiElement p = step.firstParameterExpression(az.methodCallExpression(e.getInner()));
        Encapsulator e2 = internalEncapsulator(e);
        return new AllMethodCallBased(e.getInner(), e2);
    }

    private Encapsulator internalEncapsulator(Encapsulator e) {
        if (!e.getText().contains("all")) {
            return e;
        }
        return e.getActualChildren().get(1).getActualChildren().get(1);
    }

    @Override
    public QuantifierIterator quantifierIterator(EncapsulatorIterator bgCursor, Map<Integer, List<PsiElement>> map) {
        int i = getNumberOfOccurrences(bgCursor, map);
        return new QuantifierIterator(i, i);
    }
}

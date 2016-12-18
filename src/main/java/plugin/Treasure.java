package plugin;

import com.intellij.openapi.components.ApplicationComponent;
import il.org.spartan.spartanizer.research.TipperFactory;
import il.org.spartan.spartanizer.tipping.Tipper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RoeiRaz
 *
 * holds all the active tippers
 */
public class Treasure implements ApplicationComponent {
    private ArrayList<Tipper> tippers = new ArrayList<>();

    @Override
    public void initComponent() {
        tippers.add(TipperFactory.tipper("$X1 > $X2", "$X2 > $X1", "Persisting tipper"));
        tippers.add(new il.org.spartan.spartanizer.tippers.PrefixPlusRemove());
        tippers.add(new il.org.spartan.spartanizer.tippers.IfReturnFooElseReturnBar());
        tippers.add(new il.org.spartan.spartanizer.tippers.RemoveRedundantIf());
        tippers.add(new il.org.spartan.spartanizer.tippers.BlockBreakToReturnInfiniteFor());
        tippers.add(new il.org.spartan.spartanizer.tippers.AssignmentAndReturn());
        tippers.add(new il.org.spartan.spartanizer.tippers.BlockSimplify());
        tippers.add(new il.org.spartan.spartanizer.tippers.IfDegenerateElse());
        tippers.add(new il.org.spartan.spartanizer.tippers.AssignmentToPostfixIncrement());
        tippers.add(new il.org.spartan.spartanizer.tippers.DeclarationInitializerReturnAssignment());
        tippers.add(new il.org.spartan.spartanizer.tippers.ForToForInitializers());
        tippers.add(new il.org.spartan.spartanizer.tippers.ForRenameInitializerToCent());
    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "spartanizer.plugin.Treasure";
    }

    List<Tipper> getAllTipers() {
        return this.tippers;
    }
}

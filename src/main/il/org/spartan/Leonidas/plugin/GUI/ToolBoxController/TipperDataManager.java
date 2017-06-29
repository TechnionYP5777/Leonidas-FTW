package il.org.spartan.Leonidas.plugin.GUI.ToolBoxController;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import il.org.spartan.Leonidas.plugin.Toolbox;
import il.org.spartan.Leonidas.plugin.UserControlled;
import il.org.spartan.Leonidas.plugin.tippers.LeonidasTipper;
import il.org.spartan.Leonidas.plugin.tipping.Tipper;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Sharon on 29-Jun-17.
 */
@State(
        name = "TipperDataContainer",
        storages = {
                @Storage("TipperDataContainer.xml")
        }
)
public class TipperDataManager implements PersistentStateComponent<TipperDataManager.TipperDataContainer> {
    @Nullable
    @Override
    public TipperDataContainer getState() {
        return new TipperDataContainer();
    }

    @Override
    public void loadState(TipperDataContainer state) {
        List<Tipper> tippers = Toolbox.getInstance().getAllTippers();

        // Iterate over all leonidas tippers
        tippers.stream()
                .filter(tipper -> tipper instanceof LeonidasTipper)
                .map(tipper -> (LeonidasTipper) tipper)
                .filter(tipper -> state.data.containsKey(tipper.name()))
                .forEach(tipper -> {
                    // Iterate over all generic blocks in the tipper
                    Stream.concat(tipper.getMatcher().getAllRoots().stream(), tipper.getMatcher().getAllRoots().stream())
                            .map(LeonidasTipper::getGenericElements)
                            .flatMap(Collection::stream)
                            .filter(encapsulator -> state.data.get(tipper.name()).containsKey(encapsulator.getId()))
                            .forEach(encapsulator -> {
                                // Iterate over all the fields in the generic block
                                Stream.of(encapsulator.getClass().getFields())
                                        .filter(field -> field.isAnnotationPresent(UserControlled.class))
                                        .filter(field -> state.data.get(tipper.name()).get(encapsulator.getId()).containsKey(field.getName()))
                                        .forEach(field -> {
                                            try {
                                                field.set(encapsulator, state.data.get(tipper.name()).get(encapsulator.getId()).get(field.getName()));
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        });
                            });
                });
    }

    public class TipperDataContainer {
        // A mapping from tipper name to (a mapping from basic block id to (a mapping from field name to its value)))
        public Map<String, Map<Integer, Map<String, Object>>> data = new HashMap<>();

        public TipperDataContainer() {
            List<Tipper> tippers = Toolbox.getInstance().getAllTippers();

            // Iterate over all leonidas tippers
            tippers.stream()
                    .filter(tipper -> tipper instanceof LeonidasTipper)
                    .map(tipper -> (LeonidasTipper) tipper)
                    .forEach(tipper -> {
                        data.put(tipper.name(), new HashMap<>());

                        // Iterate over all generic blocks in the tipper
                        Stream.concat(tipper.getMatcher().getAllRoots().stream(), tipper.getMatcher().getAllRoots().stream())
                                .map(LeonidasTipper::getGenericElements)
                                .flatMap(Collection::stream)
                                .forEach(encapsulator -> {
                                    data.get(tipper.name()).put(encapsulator.getId(), new HashMap<>());

                                    // Iterate over all the fields in the generic block
                                    Stream.of(encapsulator.getClass().getFields())
                                            .filter(field -> field.isAnnotationPresent(UserControlled.class))
                                            .forEach(field -> {
                                                try {
                                                    data.get(tipper.name()).get(encapsulator.getId()).put(field.getName(), field.get(encapsulator));
                                                } catch (IllegalAccessException e) {
                                                    e.printStackTrace();
                                                }
                                            });
                                });
                    });
        }
    }
}

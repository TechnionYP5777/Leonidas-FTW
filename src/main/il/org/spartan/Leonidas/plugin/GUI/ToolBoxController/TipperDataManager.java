package il.org.spartan.Leonidas.plugin.GUI.ToolBoxController;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
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
        name = "TipperDataManager",
        storages = {
                @Storage("TipperDataManager.xml")
        }
)
public class TipperDataManager implements PersistentStateComponent<TipperDataManager> {
    // A mapping from tipper name to (a mapping from basic block id to (a mapping from field name to its value)))
    public Map<String, String> data = new HashMap<>();

    public TipperDataManager() {
        data = new HashMap<>();
    }

    /**
     * @return instance of the data class
     */
    public static TipperDataManager getInstance() {
        return ServiceManager.getService(TipperDataManager.class);
    }

    @Nullable
    @Override
    public TipperDataManager getState() {
        List<Tipper> tippers = Toolbox.getInstance().getAllTippers();
        data = new HashMap<>();

        // Iterate over all leonidas tippers
        tippers.stream()
                .filter(tipper -> tipper instanceof LeonidasTipper)
                .map(tipper -> (LeonidasTipper) tipper)
                .forEach(tipper -> {
                    // Iterate over all generic blocks in the tipper
                    Stream.concat(tipper.getMatcher().getAllRoots().stream(), tipper.getMatcher().getAllRoots().stream())
                            .map(LeonidasTipper::getGenericElements)
                            .flatMap(Collection::stream)
                            .forEach(encapsulator -> {
                                // Iterate over all the fields in the generic block
                                Stream.of(encapsulator.getClass().getFields())
                                        .filter(field -> field.isAnnotationPresent(UserControlled.class))
                                        .forEach(field -> {
                                            String key = tipper.name() + "_" + encapsulator.getId() + "_" + field.getName();
                                            try {
                                                data.put(key, field.get(encapsulator).toString());
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        });
                            });
                });

        return this;
    }

    @Override
    public void loadState(TipperDataManager state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public void load() {
        List<Tipper> tippers = Toolbox.getInstance().getAllTippers();

        // Iterate over all leonidas tippers
        tippers.stream()
                .filter(tipper -> tipper instanceof LeonidasTipper)
                .map(tipper -> (LeonidasTipper) tipper)
                .forEach(tipper -> {
                    // Iterate over all generic blocks in the tipper
                    Stream.concat(tipper.getMatcher().getAllRoots().stream(), tipper.getMatcher().getAllRoots().stream())
                            .map(LeonidasTipper::getGenericElements)
                            .flatMap(Collection::stream)
                            .forEach(encapsulator -> {
                                // Iterate over all the fields in the generic block
                                Stream.of(encapsulator.getClass().getFields())
                                        .filter(field -> field.isAnnotationPresent(UserControlled.class))
                                        .filter(field -> data.containsKey(tipper.name() + "_" + encapsulator.getId() + "_" + field.getName()))
                                        .filter(field -> field.getType().isAssignableFrom(String.class))
                                        .forEach(field -> {
                                            try {
                                                field.set(encapsulator, data.get(tipper.name() + "_" + encapsulator.getId() + "_" + field.getName()));
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        });
                            });
                });
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipperDataManager that = (TipperDataManager) o;

        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}

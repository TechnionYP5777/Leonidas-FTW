package il.org.spartan.Leonidas.plugin.GUI.ToolBoxController;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

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
        // TODO
    }

    public class TipperDataContainer {
        public Map<String, Object> data = new HashMap<>();

        public TipperDataContainer() {
            // TODO: Iterate over all tippers and get the data
        }
    }
}

package il.org.spartan.Leonidas.plugin.GUI.ToolBoxController;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Sharon on 29-Jun-17.
 */
@State(
        name = "CustomLeonidasTippers",
        storages = {
                @Storage("CustomLeonidasTippers.xml")
        }
)
public class TipperDataManager implements PersistentStateComponent<TipperDataManager> {
    @Nullable
    @Override
    public TipperDataManager getState() {
        return this;
    }

    @Override
    public void loadState(TipperDataManager state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}

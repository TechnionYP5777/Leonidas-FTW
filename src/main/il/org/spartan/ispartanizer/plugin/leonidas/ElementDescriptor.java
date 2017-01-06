package il.org.spartan.ispartanizer.plugin.leonidas;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oren Afek
 * @since 06/01/17
 */
public class ElementDescriptor {

    Map<Key,Value> description;

    public ElementDescriptor(){
        description = new HashMap<>();
    }

    public ElementDescriptor addElement(Key k, Value v){
        this.description.put(k,v);
        return this;
    }

    public ElementDescriptor removeElement(Key k){
        if(this.description.containsKey(k)){
            this.description.remove(k);
        }

        return this;
    }

    public Value getElement(Key k){
        return this.description.get(k);
    }

    public Map<Key,Value> getAllElements(){
        return description;
    }

    public ElementDescriptor clear(){
        this.description.clear();
        return this;
    }

    public boolean containsKey(Key k){
        return this.description.containsKey(k);
    }
}

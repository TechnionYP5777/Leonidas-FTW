package il.org.spartan.ispartanizer.plugin.leonidas;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oren Afek
 * @since 06/01/17
 */
public class ElementDescriptor {

    Map<PsiDescriptionParameters,Value> description;

    public ElementDescriptor(){
        description = new HashMap<>();
    }

    public ElementDescriptor addElement(PsiDescriptionParameters k, Value v){
        this.description.put(k,v);
        return this;
    }

    public ElementDescriptor removeElement(PsiDescriptionParameters k){
        if(this.description.containsKey(k)){
            this.description.remove(k);
        }

        return this;
    }

    public Value getElement(PsiDescriptionParameters k){
        return this.description.get(k);
    }

    public Map<PsiDescriptionParameters,Value> getAllElements(){
        return description;
    }

    public ElementDescriptor clear(){
        this.description.clear();
        return this;
    }

    public boolean containsKey(PsiDescriptionParameters k){
        return this.description.containsKey(k);
    }
}

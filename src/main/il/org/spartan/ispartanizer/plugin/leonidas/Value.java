package il.org.spartan.ispartanizer.plugin.leonidas;

/**
 * @author Oren Afek
 * @since 2017.01.06
 */
public class Value {

    private int intValue;
    private String stringValue;

    public int getInt(){
        return intValue;
    }

    public String getString(){
        return stringValue;
    }

    public Value setInt(int value){
        this.intValue = value;
        return this;
    }

    public Value setString(String value){
        this.stringValue = value;
        return this;
    }
}

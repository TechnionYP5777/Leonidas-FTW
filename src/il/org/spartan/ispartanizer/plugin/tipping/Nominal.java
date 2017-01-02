package il.org.spartan.ispartanizer.plugin.tipping;

/**
 * Created by maorroey on 12/3/2016.
 */
public interface Nominal extends TipperCategory {
    String label = "Nominal";

    @Override default String categoryDescription() {
        return label;
    }
}
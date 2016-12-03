package plugin.tipping;

/**
 * Created by maorroey on 12/3/2016.
 */

import plugin.PreferencesResources.*;

public interface TipperCategory {
    String categoryDescription();

    /** Returns the preference group to which the tipper belongs to. This method
     * should be overridden for each tipper and should return one of the values of
     * {@link TipperGroup}
     * @return preference group this tipper belongs to */
    default TipperGroup tipperGroup() {
        return TipperGroup.find(this);
    }

    interface Abbreviation extends Nominal {
        final String label = "Abbreviation";

        @Override default String categoryDescription() {
            return label;
        }
    }

    interface Annonimization extends Nominal {
        final String label = "Unused arguments";

        @Override default String categoryDescription() {
            return label;
        }
    }

    interface Centification extends Nominal {
        String label = "Centification";

        @Override default String categoryDescription() {
            return label;
        }
    }

    /** Merge two syntactical elements into one, whereby achieving shorter core */
    interface Collapse extends Structural {
        final String label = "Collapse";

        @Override default String categoryDescription() {
            return label;
        }
    }

    /** A specialized {@link Collapse} carried out, by factoring out some common
     * element */
    interface CommnoFactoring extends Collapse { // S2
        String label = "Distributive refactoring";

        @Override default String categoryDescription() {
            return label;
        }
    }

    interface Dollarization extends Nominal {
        final String label = "Dollarization";

        @Override default String categoryDescription() {
            return label;
        }
    }

    interface EarlyReturn extends Structural {
        final String label = "Early return";

        @Override default String categoryDescription() {
            return label;
        }
    }

    /** Change expression to a more familiar structure, which is not necessarily
     * shorter */
    interface Idiomatic extends Structural {
        final String label = "Idiomatic";

        @Override default String categoryDescription() {
            return label;
        }
    }

    interface Inlining extends Structural {
        final String label = "Structural";

        @Override default String categoryDescription() {
            return label;
        }
    }

    interface InVain extends Structural {
        final String label = "NOP";

        @Override default String categoryDescription() {
            return label;
        }
    }

    interface Nanos extends Modular {
        final String label = "Nanos";

        @Override default String categoryDescription() {
            return label;
        }
    }

    interface ScopeReduction extends Structural {
        final String label = "Scope reduction";

        @Override default String categoryDescription() {
            return label;
        }
    }

    /** Use alphabetical, or some other ordering, when order does not matter */
    interface Sorting extends Idiomatic {
        final String label = "Sorting";

        @Override default String categoryDescription() {
            return label;
        }
    }

    /** Remove syntactical elements that do not change the code semantics */
    interface SyntacticBaggage extends Structural {// S1
        final String label = "Syntactic baggage";

        @Override default String categoryDescription() {
            return label;
        }
    }

    /** Replace conditional statement with the conditional operator */
    interface Ternarization extends Structural { // S3
        String label = "Ternarization";

        @Override default String categoryDescription() {
            return label;
        }
    }
}
package il.org.spartan.leonidas.plugin.leonidas;

import java.util.function.Supplier;

import static il.org.spartan.leonidas.plugin.leonidas.The.EndThe.END;

/**
 * @author Oren Afek
 * @since 3/29/2017.
 */
public interface The {

    static The the(Object... __) {
        return InnerThe.THE;
    }

    default EndThe is(Runnable template) {
        return END;
    }

    default EndThe is(Supplier<?> template) {
        return END;
    }

    default EndThe isNot(Runnable template) {
        return END;
    }

    default EndThe isNot(Supplier<?> template) {
        return END;
    }

    class EndThe {
        static final EndThe END = new EndThe();

        public <T> void ofType(Class<? extends T> __) {/**/}
    }

    class InnerThe implements The {
        static final The THE = new InnerThe();
    }
}

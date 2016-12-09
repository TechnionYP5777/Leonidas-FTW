package auxilary_layer;

import org.jetbrains.annotations.Nullable;

/**
 * @author michal cohen
 * @since 12/1/2016.
 */
public class Wrapper<T> {
    @Nullable
    protected T inner;

    public Wrapper() {
        this(null);
    }

    public Wrapper(final @Nullable T inner) {
        this.inner = inner;
    }

    @Override
    public final boolean equals(@Nullable final Object o) {
        return super.equals(o) || o != null &&
                getClass() == o.getClass() &&
                equals((Wrapper<?>) o);
    }

    /**
     * @param w JD
     * @return <code><b>true</b></code>
     * <i>iff</i> method <code>equals</code>
     * returns <code><b>true</b></code> for the wrapped objects.
     */
    public boolean equals(final Wrapper<?> w) {
        return inner == null ? w.inner == null : inner.equals(w.inner);
    }

    /**
     * @return value wrapped in this object.
     */
    public T get() {
        return inner;
    }

    @Override
    public int hashCode() {
        return inner == null ? 0 : inner.hashCode();
    }

    /**
     * set current value
     */
    public void set(final T inner) {
        this.inner = inner;
    }

    @Override
    public String toString() {
        return inner == null ? "null" : inner + "";
    }

}

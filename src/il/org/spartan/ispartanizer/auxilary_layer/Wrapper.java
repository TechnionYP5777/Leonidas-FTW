package il.org.spartan.ispartanizer.auxilary_layer;

import org.jetbrains.annotations.Nullable;

/**
 * @author michal cohen
 * @since 12/1/2016.
 */
public class Wrapper<T> {
    @Nullable
    T inner;

    public Wrapper() {
        this(null);
    }

    public Wrapper(final @Nullable T inner) {
        this.inner = inner;
    }

    @Override
    public final boolean equals(@Nullable final Object ¢) {
        return super.equals(¢) || ¢ != null &&
                getClass() == ¢.getClass() &&
                equals((Wrapper<?>) ¢);
    }

    /**
     * @param ¢ JD
     * @return <code><b>true</b></code>
     * <i>iff</i> method <code>equals</code>
     * returns <code><b>true</b></code> for the wrapped objects.
     */
    private boolean equals(final Wrapper<?> ¢) {
        return inner == null ? ¢.inner == null : inner.equals(¢.inner);
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

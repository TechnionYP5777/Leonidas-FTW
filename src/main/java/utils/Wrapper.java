package utils;

/**
 * @author michalcohen
 * @since 17-12-2016
 */
public class Wrapper<T> {
    public T inner;

    public Wrapper(T t) {
        inner = t;
    }

    public T get() {
        return inner;
    }

    public void set(T t) {
        inner = t;
    }
}

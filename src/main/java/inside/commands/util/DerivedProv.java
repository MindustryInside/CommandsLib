package inside.commands.util;

import arc.func.Prov;

public class DerivedProv<T> implements Prov<T> {
    protected final T value;

    public DerivedProv(T value) {
        this.value = value;
    }

    public static <T> Prov<T> of(T value) {
        return new DerivedProv<>(value);
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public String toString() {
        return "DerivedProv{" + value + '}';
    }
}

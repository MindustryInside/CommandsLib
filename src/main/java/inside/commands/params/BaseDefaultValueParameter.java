package inside.commands.params;

import arc.func.Prov;
import inside.commands.params.keys.ParameterKey;
import inside.commands.util.DerivedProv;

public abstract class BaseDefaultValueParameter<T> extends BaseParameter<T> implements DefaultValueParameter<T> {
    protected Prov<? extends T> defaultValueProvider;

    protected BaseDefaultValueParameter(ParameterKey<T> key, Prov<? extends T> defaultValueProvider) {
        super(key);
        this.defaultValueProvider = defaultValueProvider;
    }

    @Override
    public T getDefault() {
        return defaultValueProvider != null ? defaultValueProvider.get() : null;
    }

    @Override
    public BaseDefaultValueParameter<T> withDefault(T value) {
        this.defaultValueProvider = DerivedProv.of(value);
        return this;
    }

    @Override
    public BaseDefaultValueParameter<T> withDefault(Prov<? extends T> defaultValueProvider) {
        this.defaultValueProvider = defaultValueProvider;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
                "name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                ", defaultValueProvider=" + defaultValueProvider +
                '}';
    }
}
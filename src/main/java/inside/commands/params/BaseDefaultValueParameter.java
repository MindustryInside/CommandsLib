package inside.commands.params;

import arc.func.Prov;
import inside.commands.params.keys.ParameterKey;

public abstract class BaseDefaultValueParameter<T> extends BaseParameter<T> implements DefaultValueParameter<T> {
    protected final Prov<? extends T> defaultValueProvider;

    protected BaseDefaultValueParameter(BaseDefaultValueParameter<T> copy, Prov<? extends T> defaultValueProvider) {
        super(copy);
        this.defaultValueProvider = defaultValueProvider;
    }

    protected BaseDefaultValueParameter(ParameterKey<T> key, Prov<? extends T> defaultValueProvider) {
        super(key);
        this.defaultValueProvider = defaultValueProvider;
    }

    @Override
    public T getDefault() {
        return defaultValueProvider != null ? defaultValueProvider.get() : null;
    }

    @Override
    public abstract BaseDefaultValueParameter<T> withDefault(Prov<? extends T> prov);

    @Override
    public abstract BaseDefaultValueParameter<T> withDefault(T value);
}
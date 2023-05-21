package inside.commands.params;

import arc.func.Prov;
import inside.commands.params.keys.ParameterKey;

public abstract class BaseParameterWithDefault<T> extends BaseParameter<T> implements ParameterWithDefaultValue<T> {
    protected final Prov<? extends T> defaultValueProvider;

    protected BaseParameterWithDefault(BaseParameterWithDefault<T> copy, Prov<? extends T> defaultValueProvider) {
        super(copy);
        this.defaultValueProvider = defaultValueProvider;
    }

    protected BaseParameterWithDefault(ParameterKey<T> key, Prov<? extends T> defaultValueProvider) {
        super(key);
        this.defaultValueProvider = defaultValueProvider;
    }

    @Override
    public T getDefault() {
        return defaultValueProvider != null ? defaultValueProvider.get() : null;
    }

    @Override
    public abstract BaseParameterWithDefault<T> withDefault(Prov<? extends T> prov);

    @Override
    public abstract BaseParameterWithDefault<T> withDefault(T value);
}

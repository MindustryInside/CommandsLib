package inside.commands.params;

import arc.func.Prov;

// TODO: concise name
public interface ParameterWithDefaultValue<T> extends Parameter<T> {

    T getDefault();

    ParameterWithDefaultValue<T> withDefault(Prov<? extends T> prov);

    ParameterWithDefaultValue<T> withDefault(T value);
}

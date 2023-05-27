package inside.commands.params;

import arc.func.Prov;

public interface DefaultValueParameter<T> extends Parameter<T> {

    T getDefault();

    DefaultValueParameter<T> withDefault(Prov<? extends T> prov);

    DefaultValueParameter<T> withDefault(T value);
}
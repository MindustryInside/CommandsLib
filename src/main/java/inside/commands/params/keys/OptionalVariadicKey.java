package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalVariadicKey<T> extends OptionalKey<T>, VariadicKey<T> {

    static <T> OptionalVariadicKey<T> of(String name) {
        return new OptionalVariadicKeyImpl<>(name);
    }

    @Override
    OptionalKey<T> asSingle();
}

record OptionalVariadicKeyImpl<T>(String name) implements OptionalVariadicKey<T> {
    OptionalVariadicKeyImpl {
        Objects.requireNonNull(name);
    }

    @Override
    public OptionalKey<T> asSingle() {
        return new OptionalKeyImpl<>(name);
    }
}

package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalVariadicKey<T> extends OptionalKey<T>, VariadicKey<T> {

    static <T> OptionalVariadicKey<T> of(String name) {
        return new OptionalVariadicKeyImpl<>(Objects.requireNonNull(name));
    }

    @Override
    OptionalKey<T> asSingle();
}

record OptionalVariadicKeyImpl<T>(String name) implements OptionalVariadicKey<T> {
    @Override
    public OptionalVariadicKey<T> asVariadic() {
        return this;
    }

    @Override
    public OptionalKey<T> asSingle() {
        return new OptionalKeyImpl<>(name);
    }
}

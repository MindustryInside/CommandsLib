package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalVariadicKey<T> extends OptionalKey<T>, VariadicKey<T> permits OptionalVariadicKeyImpl {

    static <T> OptionalVariadicKey<T> of(String name) {
        return new OptionalVariadicKeyImpl<>(Objects.requireNonNull(name));
    }

    @Override
    OptionalSingleKey<T> asSingle();
}

record OptionalVariadicKeyImpl<T>(String name) implements OptionalVariadicKey<T> {
    @Override
    public OptionalSingleKey<T> asSingle() {
        return new OptionalSingleKeyImpl<>(name);
    }
}

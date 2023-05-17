package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalKey<T> extends ParameterKey<T> permits OptionalVariadicKey, OptionalKeyImpl {

    static <T> OptionalKey<T> of(String name) {
        return new OptionalKeyImpl<>(Objects.requireNonNull(name));
    }

    @Override
    OptionalVariadicKey<T> asVariadic();
}

record OptionalKeyImpl<T>(String name) implements OptionalKey<T> {
    @Override
    public OptionalVariadicKey<T> asVariadic() {
        return new OptionalVariadicKeyImpl<>(name);
    }
}

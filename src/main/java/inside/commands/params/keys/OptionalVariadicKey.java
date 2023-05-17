package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalVariadicKey<T> extends OptionalKey<T>, VariadicKey<T> {

    static <T> OptionalVariadicKey<T> of(String name) {
        return new OptionalVariadicKeyImpl<>(name);
    }
}

record OptionalVariadicKeyImpl<T>(String name) implements OptionalVariadicKey<T> {
    OptionalVariadicKeyImpl {
        Objects.requireNonNull(name);
    }
}

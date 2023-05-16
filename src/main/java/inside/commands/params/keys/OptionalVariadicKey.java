package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalVariadicKey<T> extends OptionalKey<T>, VariadicKey<T> {

    static <T> OptionalVariadicKey<T> of(String name, Class<T> type) {
        return new OptionalVariadicKeyImpl<>(name, type);
    }
}

record OptionalVariadicKeyImpl<T>(String name, Class<T> type) implements OptionalVariadicKey<T> {
    OptionalVariadicKeyImpl {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
    }
}

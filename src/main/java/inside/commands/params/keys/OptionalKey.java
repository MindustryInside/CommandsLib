package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalKey<T> extends ParameterKey<T> permits OptionalVariadicKey, OptionalKeyImpl {

    static <T> OptionalKey<T> of(String name, Class<T> type) {
        return new OptionalKeyImpl<>(name, type);
    }
}

record OptionalKeyImpl<T>(String name, Class<T> type) implements OptionalKey<T> {
    OptionalKeyImpl {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
    }
}

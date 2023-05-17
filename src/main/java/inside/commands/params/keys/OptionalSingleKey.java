package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalSingleKey<T> extends OptionalKey<T>, SingleKey<T> permits OptionalSingleKeyImpl {

    static <T> OptionalSingleKey<T> of(String name) {
        return new OptionalSingleKeyImpl<>(Objects.requireNonNull(name));
    }
}

record OptionalSingleKeyImpl<T>(String name) implements OptionalSingleKey<T> {
    @Override
    public OptionalVariadicKey<T> asVariadic() {
        return new OptionalVariadicKeyImpl<>(name);
    }
}

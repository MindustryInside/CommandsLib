package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalKey<T> extends ParameterKey<T>
        permits OptionalVariadicKey, OptionalSingleKey {

    static <T> OptionalSingleKey<T> single(String name) {
        return new OptionalSingleKeyImpl<>(Objects.requireNonNull(name));
    }

    static <T> OptionalVariadicKey<T> variadic(String name) {
        return new OptionalVariadicKeyImpl<>(Objects.requireNonNull(name));
    }
}

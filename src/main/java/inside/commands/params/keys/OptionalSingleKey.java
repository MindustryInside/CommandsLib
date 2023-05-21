package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalSingleKey<T> extends OptionalKey<T>, SingleKey<T> permits OptionalSingleKeyImpl {

    /**
     * Creates new {@code OptionalSingleKey} with specified name.
     * This method is the same as {@link OptionalKey#single(String)}.
     *
     * @param <T> The type of parameter.
     * @param name The name of parameter, not null.
     * @return A new {@code OptionalSingleKey} instance
     */
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

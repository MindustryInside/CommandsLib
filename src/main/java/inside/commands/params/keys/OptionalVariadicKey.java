package inside.commands.params.keys;

import java.util.Objects;

public sealed interface OptionalVariadicKey<T> extends OptionalKey<T>, VariadicKey<T> permits OptionalVariadicKeyImpl {

    /**
     * Creates new {@code OptionalVariadicKey} with specified name.
     * This method is the same as {@link OptionalKey#variadic(String)}.
     *
     * @param <T> The type of parameter.
     * @param name The name of parameter, not null.
     * @return A new {@code OptionalVariadicKey} instance
     */
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

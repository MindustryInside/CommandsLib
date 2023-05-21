package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatoryVariadicKey<T> extends MandatoryKey<T>, VariadicKey<T> permits MandatoryVariadicKeyImpl {

    /**
     * Creates new {@code MandatoryVariadicKey} with specified name.
     * This method is the same as {@link MandatoryKey#variadic(String)}.
     *
     * @param <T> The type of parameter.
     * @param name The name of parameter, not null.
     * @return A new {@code MandatoryVariadicKey} instance
     */
    static <T> MandatoryVariadicKey<T> of(String name) {
        return new MandatoryVariadicKeyImpl<>(Objects.requireNonNull(name));
    }

    @Override
    MandatorySingleKey<T> asSingle();
}

record MandatoryVariadicKeyImpl<T>(String name) implements MandatoryVariadicKey<T> {
    @Override
    public MandatorySingleKey<T> asSingle() {
        return new MandatorySingleKeyImpl<>(name);
    }
}

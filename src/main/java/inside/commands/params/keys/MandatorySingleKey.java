package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatorySingleKey<T> extends MandatoryKey<T>, SingleKey<T> permits MandatorySingleKeyImpl {

    /**
     * Creates new {@code MandatorySingleKey} with specified name.
     * This method is the same as {@link MandatoryKey#single(String)}.
     *
     * @param <T> The type of parameter.
     * @param name The name of parameter, not null.
     * @return A new {@code MandatorySingleKey} instance
     */
    static <T> MandatorySingleKey<T> of(String name) {
        return new MandatorySingleKeyImpl<>(Objects.requireNonNull(name));
    }
}

record MandatorySingleKeyImpl<T>(String name) implements MandatorySingleKey<T> {
    @Override
    public MandatoryVariadicKey<T> asVariadic() {
        return new MandatoryVariadicKeyImpl<>(name);
    }
}

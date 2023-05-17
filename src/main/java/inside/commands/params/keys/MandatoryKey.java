package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatoryKey<T> extends ParameterKey<T> permits MandatoryVariadicKey, MandatoryKeyImpl {

    static <T> MandatoryKey<T> of(String name) {
        return new MandatoryKeyImpl<>(Objects.requireNonNull(name));
    }

    @Override
    MandatoryVariadicKey<T> asVariadic();
}

record MandatoryKeyImpl<T>(String name) implements MandatoryKey<T> {
    @Override
    public MandatoryVariadicKey<T> asVariadic() {
        return new MandatoryVariadicKeyImpl<>(name);
    }
}

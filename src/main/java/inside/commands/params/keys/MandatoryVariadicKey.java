package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatoryVariadicKey<T> extends VariadicKey<T>, MandatoryKey<T> {

    static <T> MandatoryVariadicKey<T> of(String name) {
        return new MandatoryVariadicKeyImpl<>(Objects.requireNonNull(name));
    }

    @Override
    MandatoryKey<T> asSingle();
}

record MandatoryVariadicKeyImpl<T>(String name) implements MandatoryVariadicKey<T> {
    @Override
    public MandatoryVariadicKey<T> asVariadic() {
        return this;
    }

    @Override
    public MandatoryKey<T> asSingle() {
        return new MandatoryKeyImpl<>(name);
    }
}

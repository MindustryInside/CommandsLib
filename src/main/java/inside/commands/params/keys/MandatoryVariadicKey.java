package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatoryVariadicKey<T> extends MandatoryKey<T>, VariadicKey<T> permits MandatoryVariadicKeyImpl {

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

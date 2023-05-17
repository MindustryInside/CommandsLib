package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatoryKey<T> extends ParameterKey<T>
        permits MandatorySingleKey, MandatoryVariadicKey {

    static <T> MandatorySingleKey<T> single(String name) {
        return new MandatorySingleKeyImpl<>(Objects.requireNonNull(name));
    }

    static <T> MandatoryVariadicKey<T> variadic(String name) {
        return new MandatoryVariadicKeyImpl<>(Objects.requireNonNull(name));
    }
}

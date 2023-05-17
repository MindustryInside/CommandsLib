package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatoryKey<T> extends ParameterKey<T> permits MandatoryVariadicKey, MandatoryKeyImpl {

    static <T> MandatoryKey<T> of(String name) {
        return new MandatoryKeyImpl<>(name);
    }
}

record MandatoryKeyImpl<T>(String name) implements MandatoryKey<T> {
    MandatoryKeyImpl {
        Objects.requireNonNull(name);
    }
}

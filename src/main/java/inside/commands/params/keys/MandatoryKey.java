package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatoryKey<T> extends ParameterKey<T> permits MandatoryVariadicKey, MandatoryKeyImpl {

    static <T> MandatoryKey<T> of(String name, Class<T> type) {
        return new MandatoryKeyImpl<>(name, type);
    }
}

record MandatoryKeyImpl<T>(String name, Class<T> type) implements MandatoryKey<T> {
    MandatoryKeyImpl {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
    }
}

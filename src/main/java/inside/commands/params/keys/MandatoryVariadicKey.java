package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatoryVariadicKey<T> extends VariadicKey<T>, MandatoryKey<T> {

    static <T> MandatoryVariadicKey<T> of(String name, Class<T> type) {
        return new MandatoryVariadicKeyImpl<>(name, type);
    }
}

record MandatoryVariadicKeyImpl<T>(String name, Class<T> type) implements MandatoryVariadicKey<T> {
    MandatoryVariadicKeyImpl {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
    }
}

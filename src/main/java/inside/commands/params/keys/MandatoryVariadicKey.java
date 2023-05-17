package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatoryVariadicKey<T> extends VariadicKey<T>, MandatoryKey<T> {

    static <T> MandatoryVariadicKey<T> of(String name) {
        return new MandatoryVariadicKeyImpl<>(name);
    }
}

record MandatoryVariadicKeyImpl<T>(String name) implements MandatoryVariadicKey<T> {
    MandatoryVariadicKeyImpl {
        Objects.requireNonNull(name);
    }
}

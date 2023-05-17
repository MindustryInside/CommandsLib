package inside.commands.params.keys;

import java.util.Objects;

public sealed interface MandatorySingleKey<T> extends MandatoryKey<T>, SingleKey<T> permits MandatorySingleKeyImpl {

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

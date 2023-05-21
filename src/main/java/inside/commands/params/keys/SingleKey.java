package inside.commands.params.keys;

public sealed interface SingleKey<T> extends ParameterKey<T>
        permits MandatorySingleKey, OptionalSingleKey {

    /** {@return a new {@code VariadicKey} with same name} */
    VariadicKey<T> asVariadic();
}

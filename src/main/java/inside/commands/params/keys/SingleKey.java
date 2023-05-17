package inside.commands.params.keys;

public sealed interface SingleKey<T> extends ParameterKey<T>
        permits MandatorySingleKey, OptionalSingleKey {

    VariadicKey<T> asVariadic();
}

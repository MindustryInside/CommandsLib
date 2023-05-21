package inside.commands.params.keys;

public sealed interface VariadicKey<T> extends ParameterKey<T>
        permits MandatoryVariadicKey, OptionalVariadicKey {

    /** {@return a new {@code SingleKey} with same name} */
    SingleKey<T> asSingle();
}

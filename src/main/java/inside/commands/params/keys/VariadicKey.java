package inside.commands.params.keys;

public sealed interface VariadicKey<T> extends ParameterKey<T> permits MandatoryVariadicKey, OptionalVariadicKey {

    ParameterKey<T> asSingle();
}

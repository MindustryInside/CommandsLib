package inside.commands.params.keys;

public sealed interface ParameterKey<T>
        permits MandatoryKey, OptionalKey, SingleKey, VariadicKey {

    String name();
}

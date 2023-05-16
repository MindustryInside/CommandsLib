package inside.commands.params.keys;

public sealed interface ParameterKey<T> permits MandatoryKey, OptionalKey, VariadicKey {

    String name();

    Class<T> type();
}

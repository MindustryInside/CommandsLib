package inside.commands.params.keys;

/**
 * Class of type-safe parameter identifier.
 *
 * @param <T> The type of parameter.
 */
public sealed interface ParameterKey<T>
        permits MandatoryKey, OptionalKey, SingleKey, VariadicKey {

    /** {@return a name of parameter} not null */
    String name();
}

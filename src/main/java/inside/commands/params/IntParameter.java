package inside.commands.params;

import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.ParameterKey;
import inside.commands.params.keys.VariadicKey;

public class IntParameter extends BaseParameter<Integer> {

    protected IntParameter(String name, boolean optional, boolean variadic) {
        super(name, optional, variadic);
    }

    public static IntParameter from(ParameterKey<Integer> key) {
        boolean optional = key instanceof OptionalKey<Integer>;
        boolean variadic = key instanceof VariadicKey<Integer>;
        if (variadic) {
            return new IntVariadicParameter(key.name(), optional);
        }
        return new IntParameter(key.name(), optional, false);
    }

    @Override
    public Integer parse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalindNumberException(value);
        }
    }
}

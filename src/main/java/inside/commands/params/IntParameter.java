package inside.commands.params;

import arc.struct.Seq;
import inside.commands.params.keys.ParameterKey;
import inside.commands.params.keys.VariadicKey;

public class IntParameter extends BaseParameter<Integer> {

    protected IntParameter(ParameterKey<Integer> key) {
        super(key);
    }

    protected IntParameter(String name, boolean optional, boolean variadic) {
        super(name, optional, variadic);
    }

    public static IntParameter from(ParameterKey<Integer> key) {
        return key instanceof VariadicKey<Integer> v
                ? new IntVariadicParameter(v)
                : new IntParameter(key);
    }

    @Override
    public Integer parse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidNumberException(value);
        }
    }
}

class IntVariadicParameter extends IntParameter implements VariadicParameter<Integer> {

    IntVariadicParameter(VariadicKey<Integer> key) {
        super(key);
    }

    @Override
    public Seq<Integer> parseMultiple(String value) {
        String[] parts = value.split(" ");
        Seq<Integer> values = new Seq<>(true, parts.length, Integer.class);
        for (String part : parts) {
            values.add(parse(part));
        }
        return values;
    }
}

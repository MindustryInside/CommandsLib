package inside.commands.params;

import arc.struct.Seq;
import inside.commands.params.keys.ParameterKey;
import inside.commands.params.keys.VariadicKey;

public class StringParameter extends BaseParameter<String> {

    protected StringParameter(ParameterKey<String> key) {
        super(key);
    }

    public static StringParameter from(ParameterKey<String> key) {
        return key instanceof VariadicKey<String> v
                ? new StringVariadicParameter(v)
                : new StringParameter(key);
    }

    @Override
    public String parse(String value) {
        return value;
    }
}

class StringVariadicParameter extends StringParameter implements VariadicParameter<String> {

    protected StringVariadicParameter(VariadicKey<String> key) {
        super(key);
    }

    @Override
    public Seq<String> parseMultiple(String value) throws InvalidParameterException {
        return Seq.with(value.split(" "));
    }
}

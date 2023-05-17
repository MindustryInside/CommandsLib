package inside.commands.params;

import arc.struct.Seq;
import inside.commands.MessageService;
import inside.commands.params.keys.ParameterKey;
import inside.commands.params.keys.VariadicKey;

import java.security.InvalidParameterException;

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
    public String parse(MessageService service, String value) {
        return value;
    }
}

class StringVariadicParameter extends StringParameter implements VariadicParameter<String> {

    protected StringVariadicParameter(VariadicKey<String> key) {
        super(key);
    }

    @Override
    public Seq<String> parseMultiple(MessageService messageService, String value) throws InvalidParameterException {
        return Seq.with(value.split(" "));
    }
}

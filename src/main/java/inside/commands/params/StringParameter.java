package inside.commands.params;

import inside.commands.MessageService;
import inside.commands.params.keys.ParameterKey;

public class StringParameter extends BaseParameter<String> {

    protected StringParameter(ParameterKey<String> key) {
        super(key);
    }

    public static StringParameter from(ParameterKey<String> key) {
        return new StringParameter(key);
    }

    @Override
    public String parse(MessageService service, String value) {
        return value;
    }
}

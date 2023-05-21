package inside.commands.params;

import inside.commands.MessageService;
import inside.commands.params.keys.SingleKey;

public class StringParameter extends BaseParameter<String> {

    protected StringParameter(SingleKey<String> key) {
        super(key);
    }

    public static StringParameter from(SingleKey<String> key) {
        return new StringParameter(key);
    }

    @Override
    public String parse(MessageService service, String value) {
        return value;
    }
}

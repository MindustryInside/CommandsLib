package inside.commands.params.impl;

import arc.func.Prov;
import inside.commands.MessageService;
import inside.commands.params.BaseDefaultValueParameter;
import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.ParameterKey;
import inside.commands.util.DerivedProv;

public class StringParameter extends BaseDefaultValueParameter<String> {

    protected StringParameter(ParameterKey<String> key, Prov<? extends String> defaultValueProvider) {
        super(key, defaultValueProvider);
    }

    protected StringParameter(StringParameter copy, Prov<? extends String> defaultValueProvider) {
        super(copy, defaultValueProvider);
    }

    public static StringParameter from(ParameterKey<String> key) {
        return new StringParameter(key, null);
    }

    public static StringParameter from(OptionalKey<String> key, Prov<String> defaultValueProvider) {
        return new StringParameter(key, defaultValueProvider);
    }

    public static StringParameter from(OptionalKey<String> key, String defaultValue) {
        return new StringParameter(key, DerivedProv.of(defaultValue));
    }

    @Override
    public StringParameter withDefault(Prov<? extends String> defaultValueProvider) {
        return new StringParameter(this, defaultValueProvider);
    }

    @Override
    public StringParameter withDefault(String defaultValue) {
        return new StringParameter(this, DerivedProv.of(defaultValue));
    }

    @Override
    public String parse(MessageService service, String value) {
        return value;
    }
}

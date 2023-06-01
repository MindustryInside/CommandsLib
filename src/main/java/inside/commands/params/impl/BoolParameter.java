package inside.commands.params.impl;

import arc.func.Prov;
import inside.commands.MessageService;
import inside.commands.params.BaseDefaultValueParameter;
import inside.commands.params.keys.OptionalSingleKey;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.DerivedProv;

public class BoolParameter extends BaseDefaultValueParameter<Boolean> {

    protected BoolParameter(SingleKey<Boolean> key, Prov<? extends Boolean> defaultValueProvider) {
        super(key, defaultValueProvider);
    }

    public static BoolParameter from(SingleKey<Boolean> key) {
        return new BoolParameter(key, null);
    }

    public static BoolParameter from(OptionalSingleKey<Boolean> key, Prov<Boolean> defaultValueProvider) {
        return new BoolParameter(key, defaultValueProvider);
    }

    public static BoolParameter from(OptionalSingleKey<Boolean> key, Boolean defaultValue) {
        return new BoolParameter(key, DerivedProv.of(defaultValue));
    }

    @Override
    public Boolean parse(MessageService service, String value) {
        return switch (value.toLowerCase()) {
            case "y", "yes", "true" -> true;
            case "n", "no", "false" -> false;
            default -> {
                service.sendError(service.prefix("invalid-boolean"), value);
                yield null;
            }
        };
    }
}
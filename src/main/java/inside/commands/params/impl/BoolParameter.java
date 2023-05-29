package inside.commands.params.impl;

import arc.func.Prov;
import inside.commands.MessageService;
import inside.commands.params.BaseDefaultValueParameter;
import inside.commands.params.keys.*;

import java.util.Objects;
import java.util.Optional;

public class BoolParameter extends BaseDefaultValueParameter<Boolean> {

    protected BoolParameter(SingleKey<Boolean> key, Prov<? extends Boolean> defaultValueProvider) {
        super(key, defaultValueProvider);
    }

    protected BoolParameter(BoolParameter copy, Prov<? extends Boolean> defaultValueProvider) {
        super(copy, defaultValueProvider);
    }

    public static BoolParameter from(MandatorySingleKey<Boolean> key) {
        return new BoolParameter(key, null);
    }

    public static BoolParameter from(OptionalSingleKey<Boolean> key) {
        return new BoolParameter(key, null);
    }

    public static BoolParameter from(OptionalSingleKey<Boolean> key, Prov<Boolean> defaultValueProvider) {
        return new BoolParameter(key, defaultValueProvider);
    }

    public static BoolParameter from(OptionalSingleKey<Boolean> key, Boolean defaultValue) {
        return new BoolParameter(key, () -> defaultValue);
    }

    @Override
    public BoolParameter withDefault(Prov<? extends Boolean> defaultValueProvider) {
        return new BoolParameter(this, defaultValueProvider);
    }

    @Override
    public BoolParameter withDefault(Boolean defaultValue) {
        return new BoolParameter(this, () -> defaultValue);
    }

    @Override
    public Boolean parse(MessageService service, String value) {
        return switch (value.toLowerCase()) {
            case "y", "yes", "true" -> true;
            case "n", "no", "false" -> false;
            default -> {
                service.sendError(service.error("invalid-boolean"), value);
                yield null;
            }
        };
    }

    @Override
    public String toString() {
        return "BoolParameter{" +
                "name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                '}';
    }
}
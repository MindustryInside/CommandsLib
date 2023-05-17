package inside.commands.params;

import inside.commands.MessageService;
import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.ParameterKey;
import inside.commands.params.keys.VariadicKey;

public abstract class BaseParameter<T> implements Parameter<T> {

    protected final String name;
    protected final boolean optional;
    protected final boolean variadic;

    protected BaseParameter(BaseParameter<T> copy) {
        this.name = copy.name;
        this.optional = copy.optional;
        this.variadic = copy.variadic;
    }

    protected BaseParameter(ParameterKey<T> key) {
        this.name = key.name();
        this.optional = key instanceof OptionalKey<T>;
        this.variadic = key instanceof VariadicKey<T>;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final boolean optional() {
        return optional;
    }

    @Override
    public final boolean variadic() {
        return variadic;
    }

    @Override
    public abstract T parse(MessageService service, String value);

    @Override
    public String toString() {
        return "BaseParameter{" +
                "name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                '}';
    }
}

package inside.commands.params;

import java.util.Objects;

public abstract class BaseParameter<T> implements Parameter<T> {

    protected final String name;
    protected final boolean optional;
    protected final boolean variadic;

    protected BaseParameter(String name, boolean optional, boolean variadic) {
        this.name = Objects.requireNonNull(name);
        this.optional = optional;
        this.variadic = variadic;
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
    public abstract T parse(String value);
}

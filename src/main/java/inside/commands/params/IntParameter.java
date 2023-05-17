package inside.commands.params;

import arc.struct.Seq;
import inside.commands.MessageService;
import inside.commands.params.keys.ParameterKey;
import inside.commands.params.keys.VariadicKey;

import java.util.Objects;
import java.util.Optional;

public class IntParameter extends BaseParameter<Integer> {

    protected final Integer minValue;
    protected final Integer maxValue;

    protected IntParameter(ParameterKey<Integer> key) {
        super(key);
        this.maxValue = null;
        this.minValue = null;
    }

    protected IntParameter(IntParameter copy, Integer minValue, Integer maxValue) {
        super(copy);
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    // TODO: docs
    // exclusive
    public Optional<Integer> minValue() {
        return Optional.ofNullable(minValue);
    }

    // exclusive
    public Optional<Integer> maxValue() {
        return Optional.ofNullable(maxValue);
    }

    public IntParameter withMinValue(Integer minValue) {
        if (Objects.equals(this.minValue, minValue)) return this;
        return new IntParameter(this, minValue, maxValue);
    }

    public IntParameter withMaxValue(Integer maxValue) {
        if (Objects.equals(this.maxValue, maxValue)) return this;
        return new IntParameter(this, minValue, maxValue);
    }

    public IntParameter withInRange(Integer minValue, Integer maxValue) {
        if (Objects.equals(this.minValue, minValue) && Objects.equals(this.maxValue, maxValue)) return this;
        return new IntParameter(this, minValue, maxValue);
    }

    public static IntParameter from(ParameterKey<Integer> key) {
        return key instanceof VariadicKey<Integer> v
                ? new IntVariadicParameter(v)
                : new IntParameter(key);
    }

    @Override
    public Integer parse(MessageService service, String value) {
        int val;
        try {
            val = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            service.sendError(service.error("invalid-number"), value);
            return null;
        }

        if (minValue != null && val < minValue) {
            service.sendError(service.error("less-than-min"), val);
            return null;
        }
        if (maxValue != null && val > maxValue) {
            service.sendError(service.error("greater-than-max"), val);
            return null;
        }
        return val;
    }

    @Override
    public String toString() {
        return "IntParameter{" +
                "minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                '}';
    }
}

class IntVariadicParameter extends IntParameter implements VariadicParameter<Integer> {

    IntVariadicParameter(VariadicKey<Integer> key) {
        super(key);
    }

    IntVariadicParameter(IntVariadicParameter copy, Integer minValue, Integer maxValue) {
        super(copy, minValue, maxValue);
    }

    @Override
    public IntParameter withMaxValue(Integer maxValue) {
        if (Objects.equals(this.maxValue, maxValue)) return this;
        return new IntVariadicParameter(this, minValue, maxValue);
    }

    @Override
    public IntParameter withMinValue(Integer minValue) {
        if (Objects.equals(this.minValue, minValue)) return this;
        return new IntVariadicParameter(this, minValue, maxValue);
    }

    @Override
    public IntParameter withInRange(Integer minValue, Integer maxValue) {
        if (Objects.equals(this.minValue, minValue) && Objects.equals(this.maxValue, maxValue)) return this;
        return new IntVariadicParameter(this, minValue, maxValue);
    }

    @Override
    public Seq<Integer> parseMultiple(MessageService messageService, String value) {
        String[] parts = value.split(" ");
        Seq<Integer> values = new Seq<>(true, parts.length, Integer.class);
        for (String part : parts) {
            Integer i = parse(messageService, part);
            if (i == null) {
                return null;
            }
            values.add(i);
        }
        return values;
    }
}

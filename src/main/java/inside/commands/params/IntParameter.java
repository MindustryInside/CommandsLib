package inside.commands.params;

import arc.struct.Seq;
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

    public Optional<Integer> minValue() {
        return Optional.ofNullable(minValue);
    }

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

    public static IntParameter from(ParameterKey<Integer> key) {
        return key instanceof VariadicKey<Integer> v
                ? new IntVariadicParameter(v)
                : new IntParameter(key);
    }

    @Override
    public Integer parse(String value) {
        int val;
        try {
            val = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidNumberException(value, InvalidNumberException.Type.INVALID);
        }

        if (minValue != null && val < minValue) {
            throw new InvalidNumberException(value, InvalidNumberException.Type.LESS_MIN);
        }
        if (maxValue != null && val > maxValue) {
            throw new InvalidNumberException(value, InvalidNumberException.Type.GREATER_MAX);
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
    public Seq<Integer> parseMultiple(String value) {
        String[] parts = value.split(" ");
        Seq<Integer> values = new Seq<>(true, parts.length, Integer.class);
        for (String part : parts) {
            values.add(parse(part));
        }
        return values;
    }
}

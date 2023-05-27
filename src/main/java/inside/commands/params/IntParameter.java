package inside.commands.params;

import arc.func.Prov;
import inside.commands.MessageService;
import inside.commands.params.keys.*;

import java.util.Objects;
import java.util.Optional;

public class IntParameter extends BaseDefaultValueParameter<Integer> {

    protected final Integer minValue;
    protected final Integer maxValue;

    protected IntParameter(SingleKey<Integer> key, Prov<Integer> defaultValueProvider) {
        super(key, defaultValueProvider);
        this.maxValue = null;
        this.minValue = null;
    }

    protected IntParameter(IntParameter copy, Prov<? extends Integer> defaultValueProvider, Integer minValue, Integer maxValue) {
        super(copy, defaultValueProvider);
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    /** {@return the <b>min</b> acceptable integer, if present} (exclusive) */
    public Optional<Integer> minValue() {
        return Optional.ofNullable(minValue);
    }

    /** {@return the <b>max</b> acceptable integer, if present} (exclusive) */
    public Optional<Integer> maxValue() {
        return Optional.ofNullable(maxValue);
    }

    public IntParameter withMinValue(Integer minValue) {
        if (Objects.equals(this.minValue, minValue)) return this;
        return new IntParameter(this, defaultValueProvider, minValue, maxValue);
    }

    public IntParameter withMaxValue(Integer maxValue) {
        if (Objects.equals(this.maxValue, maxValue)) return this;
        return new IntParameter(this, defaultValueProvider, minValue, maxValue);
    }

    public IntParameter withRange(Integer minValue, Integer maxValue) {
        if (Objects.equals(this.minValue, minValue) && Objects.equals(this.maxValue, maxValue)) return this;
        return new IntParameter(this, defaultValueProvider, minValue, maxValue);
    }

    public static IntParameter from(MandatorySingleKey<Integer> key) {
        return new IntParameter(key, null);
    }

    public static IntParameter from(OptionalSingleKey<Integer> key) {
        return new IntParameter(key, null);
    }

    public static IntParameter from(OptionalSingleKey<Integer> key, Prov<Integer> defaultValueProvider) {
        return new IntParameter(key, defaultValueProvider);
    }

    public static IntParameter from(OptionalSingleKey<Integer> key, Integer defaultValueProvider) {
        return new IntParameter(key, () -> defaultValueProvider);
    }

    @Override
    public IntParameter withDefault(Prov<? extends Integer> prov) {
        return new IntParameter(this, prov, minValue, maxValue);
    }

    @Override
    public IntParameter withDefault(Integer value) {
        return new IntParameter(this, () -> value, minValue, maxValue);
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
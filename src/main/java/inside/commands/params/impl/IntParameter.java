package inside.commands.params.impl;

import arc.func.Prov;
import inside.commands.MessageService;
import inside.commands.params.BaseDefaultValueParameter;
import inside.commands.params.keys.OptionalSingleKey;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.DerivedProv;

import java.util.Optional;

public class IntParameter extends BaseDefaultValueParameter<Integer> {
    protected Integer minValue;
    protected Integer maxValue;

    protected IntParameter(SingleKey<Integer> key, Prov<? extends Integer> defaultValueProvider) {
        super(key, defaultValueProvider);
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
        this.minValue = minValue;
        return this;
    }

    public IntParameter withMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public IntParameter withRange(Integer minValue, Integer maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        return this;
    }

    public static IntParameter from(SingleKey<Integer> key) {
        return new IntParameter(key, null);
    }

    public static IntParameter from(OptionalSingleKey<Integer> key, Prov<Integer> defaultValueProvider) {
        return new IntParameter(key, defaultValueProvider);
    }

    public static IntParameter from(OptionalSingleKey<Integer> key, Integer defaultValue) {
        return new IntParameter(key, DerivedProv.of(defaultValue));
    }

    @Override
    public Integer parse(MessageService service, String value) {
        int val;
        try {
            val = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            service.sendError(service.prefix("invalid-number"), value);
            return null;
        }

        if (minValue != null && val < minValue) {
            service.sendError(service.prefix("less-than-min"), minValue);
            return null;
        }

        if (maxValue != null && val > maxValue) {
            service.sendError(service.prefix("greater-than-max"), maxValue);
            return null;
        }

        return val;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
                "name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                ", defaultValueProvider=" + defaultValueProvider +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}
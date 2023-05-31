package inside.commands.params.impl;

import arc.func.Prov;
import inside.commands.MessageService;
import inside.commands.params.BaseDefaultValueParameter;
import inside.commands.params.keys.*;
import inside.commands.util.DerivedProv;

import java.util.Objects;
import java.util.Optional;

public class IntParameter extends BaseDefaultValueParameter<Integer> {

    protected final Integer minValue;
    protected final Integer maxValue;

    protected IntParameter(SingleKey<Integer> key, Prov<? extends Integer> defaultValueProvider) {
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
    public IntParameter withDefault(Prov<? extends Integer> defaultValueProvider) {
        return new IntParameter(this, defaultValueProvider, minValue, maxValue);
    }

    @Override
    public IntParameter withDefault(Integer defaultValue) {
        return new IntParameter(this, DerivedProv.of(defaultValue), minValue, maxValue);
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
        return "IntParameter{" +
                "minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", defaultValueProvider=" + defaultValueProvider +
                ", name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                '}';
    }
}
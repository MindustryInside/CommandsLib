package inside.commands;

import arc.func.Prov;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import inside.commands.params.keys.*;

import java.util.Locale;
import java.util.Optional;

public abstract sealed class CommandContext permits ClientCommandContext, ServerCommandContext {
    private final Locale locale;
    private final BundleProvider bundleProvider;
    private final ObjectMap<String, ?> parameters;

    CommandContext(Locale locale, BundleProvider bundleProvider, ObjectMap<String, ?> parameters) {
        this.locale = locale;
        this.bundleProvider = bundleProvider;
        this.parameters = parameters;
    }

    public Locale locale() {
        return locale;
    }

    public BundleProvider bundleProvider() {
        return bundleProvider;
    }

    public <T> T get(MandatoryKey<T> key) {
        return get0(key);
    }

    public <T> Optional<T> get(OptionalKey<T> key) {
        return Optional.ofNullable(get0(key));
    }

    public <T> T get(OptionalKey<T> key, T defaultValue) {
        T t = get0(key);
        return t != null ? t : defaultValue;
    }

    public <T> T getOrDefault(OptionalKey<T> key, Prov<T> defaultValueProv) {
        T t = get0(key);
        return t != null ? t : defaultValueProv.get();
    }

    public <T> Seq<T> get(MandatoryVariadicKey<T> key) {
        return get0(key);
    }

    public <T> Optional<Seq<T>> get(OptionalVariadicKey<T> key) {
        return Optional.ofNullable(get0(key));
    }

    public <T> Seq<T> get(OptionalVariadicKey<T> key, Seq<T> defaultValue) {
        Seq<T> t = get0(key);
        return t != null ? t : defaultValue;
    }

    public <T> Seq<T> getOrDefault(OptionalVariadicKey<T> key, Prov<Seq<T>> defaultValueProv) {
        Seq<T> t = get0(key);
        return t != null ? t : defaultValueProv.get();
    }

    // bundle provider methods
    // =======================

    public String get(String key) {
        return bundleProvider.get(key, locale);
    }

    public String format(String key, Locale locale, Object... values) {
        if (values.length == 0) {
            return bundleProvider.get(key, locale);
        }
        return bundleProvider.format(key, locale, values);
    }

    // private methods
    // ===============

    private <T> T get0(ParameterKey<?> key) {
        @SuppressWarnings("unchecked")
        T o = (T) parameters.get(key.name());
        return o;
    }
}

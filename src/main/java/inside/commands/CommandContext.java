package inside.commands;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Nullable;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.MandatoryVariadicKey;
import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.OptionalVariadicKey;
import mindustry.gen.Player;

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

    public <T> Optional<Seq<T>> get(OptionalVariadicKey<T> key) {
        @SuppressWarnings("unchecked")
        var o = (Seq<T>) parameters.get(key.name());
        return Optional.ofNullable(o);
    }

    public <T> Seq<T> get(MandatoryVariadicKey<T> key) {
        @SuppressWarnings("unchecked")
        var o = (Seq<T>) parameters.get(key.name());
        return o;
    }

    public <T> T get(MandatoryKey<T> key) {
        @SuppressWarnings("unchecked")
        T o = (T) parameters.get(key.name());
        return o;
    }

    public <T> Optional<T> get(OptionalKey<T> key) {
        @SuppressWarnings("unchecked")
        T o = (T) parameters.get(key.name());
        return Optional.ofNullable(o);
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
}

package inside.commands;

import arc.func.Prov;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import inside.commands.params.keys.*;

import java.util.Locale;
import java.util.Optional;

public abstract sealed class CommandContext permits ClientCommandContext, ServerCommandContext {
    private final Locale locale;
    private final ObjectMap<String, ?> parameters;
    private final MessageService messageService;

    CommandContext(Locale locale, ObjectMap<String, ?> parameters, MessageService messageService) {
        this.locale = locale;
        this.parameters = parameters;
        this.messageService = messageService;
    }

    public Locale locale() {
        return locale;
    }

    public MessageService messageService() {
        return messageService;
    }

    public <T> T get(MandatorySingleKey<T> key) {
        return get0(key);
    }

    public <T> Optional<T> get(OptionalSingleKey<T> key) {
        return Optional.ofNullable(get0(key));
    }

    public <T> T get(OptionalSingleKey<T> key, T defaultValue) {
        T t = get0(key);
        return t != null ? t : defaultValue;
    }

    public <T> T getOrDefault(OptionalSingleKey<T> key, Prov<T> defaultValueProv) {
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
        var t = get0(key);
        return t != null ? t : defaultValue;
    }

    public <T> Seq<T> getOrDefault(OptionalVariadicKey<T> key, Prov<Seq<T>> defaultValueProv) {
        var t = get0(key);
        return t != null ? t : defaultValueProv.get();
    }

    // private methods
    // ===============

    private <T> T get0(SingleKey<T> key) {
        @SuppressWarnings("unchecked")
        T o = (T) parameters.get(key.name());
        return o;
    }

    private <T> Seq<T> get0(VariadicKey<T> key) {
        @SuppressWarnings("unchecked")
        var o = (Seq<T>) parameters.get(key.name());
        return o;
    }
}

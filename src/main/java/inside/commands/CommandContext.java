package inside.commands;

import arc.func.Prov;
import arc.struct.ObjectMap;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.ParameterKey;

import java.util.Objects;
import java.util.Optional;

public abstract sealed class CommandContext permits ClientCommandContext, ServerCommandContext {
    private final ObjectMap<String, ?> parameters;
    private final MessageService messageService;

    CommandContext(ObjectMap<String, ?> parameters, MessageService messageService) {
        this.parameters = parameters;
        this.messageService = messageService;
    }

    public MessageService messageService() {
        return messageService;
    }

    public <T> T get(MandatoryKey<T> key) {
        return get0(key);
    }

    // TODO: concise name
    public <T> T getAsserted(OptionalKey<T> key) {
        return Objects.requireNonNull(get0(key), () -> "No value for key: " + key);
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

    // private methods
    // ===============

    private <T> T get0(ParameterKey<T> key) {
        @SuppressWarnings("unchecked")
        T o = (T) parameters.get(key.name());
        return o;
    }
}

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

public final class CommandContext {
    private final Locale locale;
    private final Player player;
    private final ObjectMap<String, ?> parameters;

    CommandContext(Locale locale, Player player, ObjectMap<String, ?> parameters) {
        this.locale = locale;
        this.player = player;
        this.parameters = parameters;
    }

    public Locale locale() {
        return locale;
    }

    @Nullable // if it is server command
    public Player player() {
        return player;
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
}

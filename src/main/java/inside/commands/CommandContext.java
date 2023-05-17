package inside.commands;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Nullable;
import inside.commands.params.InvalidParameterException;
import inside.commands.params.keys.*;
import mindustry.gen.Player;

import java.util.Optional;

public final class CommandContext {
    private final Player player;
    private final ObjectMap<String, ?> parameters;

    CommandContext(Player player, ObjectMap<String, ?> parameters) {
        this.player = player;
        this.parameters = parameters;
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

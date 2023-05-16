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
        Object o = parameters.get(key.name());
        if (o instanceof InvalidParameterException e) {
            throw e;
        }
        @SuppressWarnings("unchecked")
        var t = (Seq<T>) o;
        return Optional.ofNullable(t);
    }

    public <T> Seq<T> get(MandatoryVariadicKey<T> key) {
        Object o = parameters.get(key.name());
        if (o instanceof InvalidParameterException e) {
            throw e;
        }
        @SuppressWarnings("unchecked")
        var t = (Seq<T>) o;
        return t;
    }

    public <T> T get(MandatoryKey<T> key) {
        Object o = parameters.get(key.name());
        if (o instanceof InvalidParameterException e) {
            throw e;
        }
        return key.type().cast(o);
    }

    public <T> Optional<T> get(OptionalKey<T> key) {
        Object o = parameters.get(key.name());
        if (o instanceof InvalidParameterException e) {
            throw e;
        }
        return Optional.ofNullable(key.type().cast(o));
    }
}

package inside.commands.params.impl;

import inside.commands.MessageService;
import inside.commands.params.BaseParameter;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.*;
import mindustry.gen.Player;

import java.util.*;

public class PlayerParameter extends BaseParameter<Player> {

    protected final Set<SearchOption> options;

    protected PlayerParameter(SingleKey<Player> key) {
        this(key, EnumSet.allOf(SearchOption.class));
    }

    protected PlayerParameter(SingleKey<Player> key, Set<SearchOption> options) {
        super(key);
        this.options = options;
    }

    protected PlayerParameter(PlayerParameter copy, Set<SearchOption> options) {
        super(copy);
        this.options = options;
    }

    public static PlayerParameter from(SingleKey<Player> key) {
        return new PlayerParameter(key);
    }

    public static PlayerParameter from(SingleKey<Player> key, Set<SearchOption> options) {
        return new PlayerParameter(key, options);
    }

    public Set<SearchOption> options() {
        return options;
    }

    public PlayerParameter withOptions(SearchOption... options) {
        var newOptions = EnumSet.copyOf(Arrays.asList(options));
        if (newOptions.equals(this.options)) return this;

        return new PlayerParameter(this, newOptions);
    }

    public PlayerParameter withOptions(Collection<SearchOption> options) {
        var newOptions = EnumSet.copyOf(options);
        if (newOptions.equals(this.options)) return this;

        return new PlayerParameter(this, newOptions);
    }

    @Override
    public Player parse(MessageService service, String value) {
        var players = Search.players(value, options);
        if (players.isEmpty()) {
            service.sendError(service.error("player-not-found"), value);
            return null;
        } else if (players.size > 1) {
            service.sendError(service.error("too-many-players-found"), value);
            return null;
        }

        return players.first();
    }

    @Override
    public String toString() {
        return "PlayerParameter{" +
                "options=" + options +
                ", name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                '}';
    }
}

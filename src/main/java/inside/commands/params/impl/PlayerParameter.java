package inside.commands.params.impl;

import inside.commands.MessageService;
import inside.commands.params.BaseSearchParameter;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.*;
import mindustry.gen.Player;

import java.util.*;

public class PlayerParameter extends BaseSearchParameter<Player> {

    protected PlayerParameter(SingleKey<Player> key) {
        super(key);
    }

    protected PlayerParameter(SingleKey<Player> key, Set<SearchOption> options) {
        super(key, options);
    }

    public static PlayerParameter from(SingleKey<Player> key) {
        return new PlayerParameter(key);
    }

    public static PlayerParameter from(SingleKey<Player> key, Set<SearchOption> options) {
        return new PlayerParameter(key, options);
    }

    @Override
    public Player parse(MessageService service, String value) {
        var players = Search.players(value, options);
        if (players.isEmpty()) {
            service.sendError(service.prefix("player-not-found"), value);
            return null;
        } else if (players.size > 1) {
            service.sendError(service.prefix("too-many-players-found"), value);
            return null;
        }

        return players.first();
    }
}
package inside.commands.params.impl;

import inside.commands.MessageService;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.*;
import mindustry.net.Administration.PlayerInfo;

import java.util.*;

public class PlayerInfoParameter extends SearchParameter<PlayerInfo> {

    protected PlayerInfoParameter(SingleKey<PlayerInfo> key) {
        super(key);
    }

    protected PlayerInfoParameter(SingleKey<PlayerInfo> key, Set<SearchOption> options) {
        super(key, options);
    }

    protected PlayerInfoParameter(PlayerInfoParameter copy, Set<SearchOption> options) {
        super(copy, options);
    }

    public static PlayerInfoParameter from(SingleKey<PlayerInfo> key) {
        return new PlayerInfoParameter(key);
    }

    public static PlayerInfoParameter from(SingleKey<PlayerInfo> key, Set<SearchOption> options) {
        return new PlayerInfoParameter(key, options);
    }

    @Override
    public PlayerInfoParameter withOptions(SearchOption... options) {
        var newOptions = EnumSet.copyOf(Arrays.asList(options));
        if (newOptions.equals(this.options)) return this;

        return new PlayerInfoParameter(this, newOptions);
    }

    @Override
    public PlayerInfoParameter withOptions(Collection<SearchOption> options) {
        var newOptions = EnumSet.copyOf(options);
        if (newOptions.equals(this.options)) return this;

        return new PlayerInfoParameter(this, newOptions);
    }

    @Override
    public PlayerInfo parse(MessageService service, String value) {
        var playerInfo = Search.playerInfo(value, options);
        if (playerInfo.isEmpty()) {
            service.sendError(service.prefix("player-not-found"), value);
            return null;
        } else if (playerInfo.size > 1) {
            service.sendError(service.prefix("too-many-players-found"), value);
            return null;
        }

        return playerInfo.first();
    }

    @Override
    public String toString() {
        return "PlayerInfoParameter{" +
                "options=" + options +
                ", name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                '}';
    }
}
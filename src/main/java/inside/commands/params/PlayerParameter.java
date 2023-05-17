package inside.commands.params;

import arc.struct.Seq;
import arc.util.Strings;
import inside.commands.MessageService;
import inside.commands.params.keys.ParameterKey;
import inside.commands.params.keys.VariadicKey;
import mindustry.gen.Groups;
import mindustry.gen.Player;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class PlayerParameter extends BaseParameter<Player> {

    protected final Set<SearchOption> options;

    protected PlayerParameter(ParameterKey<Player> key) {
        super(key);
        this.options = Set.of();
    }

    protected PlayerParameter(PlayerParameter copy, Set<SearchOption> options) {
        super(copy);
        this.options = options;
    }

    public static PlayerParameter from(ParameterKey<Player> key) {
        return key instanceof VariadicKey<Player> v
                ? new PlayerVariadicParameter(v)
                : new PlayerParameter(key);
    }

    public Set<SearchOption> options() {
        return options;
    }

    public PlayerParameter withOptions(SearchOption... options) {
        var newOptions = immutableEnumSetOf(Arrays.asList(options), SearchOption.class);
        if (newOptions.equals(this.options)) return this;
        return new PlayerParameter(this, newOptions);
    }

    public PlayerParameter withOptions(Iterable<SearchOption> options) {
        var newOptions = immutableEnumSetOf(options, SearchOption.class);
        if (newOptions.equals(this.options)) return this;
        return new PlayerParameter(this, newOptions);
    }

    @Override
    public Player parse(MessageService messageService, String value) {
        String transformed = value;
        if (options.contains(SearchOption.STRIP_COLORS_AND_GLYPHS)) {
            transformed = stripAll(value);
        }

        boolean ignoreCase = options.contains(SearchOption.IGNORE_CASE);
        for (Player player : Groups.player) {
            if (ignoreCase) {
                if (transformed.equalsIgnoreCase(player.name)) {
                    return player;
                }
            } else {
                if (transformed.equals(player.name)) {
                    return player;
                }
            }
        }

        messageService.sendError("No player with name {0} found", value);
        return null;
    }

    public enum SearchOption {
        IGNORE_CASE,
        STRIP_COLORS_AND_GLYPHS
    }

    static <T extends Enum<T>> Set<T> immutableEnumSetOf(Iterable<T> iterable, Class<T> type) {
        var s = EnumSet.noneOf(type);
        for (T e : iterable) {
            s.add(e);
        }
        return Collections.unmodifiableSet(s);
    }

    static String stripAll(String text) {
        return Strings.stripColors(Strings.stripGlyphs(text));
    }
}

class PlayerVariadicParameter extends PlayerParameter implements VariadicParameter<Player> {

    protected PlayerVariadicParameter(VariadicKey<Player> key) {
        super(key);
    }

    protected PlayerVariadicParameter(PlayerVariadicParameter copy, Set<SearchOption> options) {
        super(copy, options);
    }

    public PlayerParameter withOptions(SearchOption... options) {
        var newOptions = immutableEnumSetOf(Arrays.asList(options), SearchOption.class);
        if (newOptions.equals(this.options)) return this;
        return new PlayerVariadicParameter(this, newOptions);
    }

    public PlayerParameter withOptions(Iterable<SearchOption> options) {
        var newOptions = immutableEnumSetOf(options, SearchOption.class);
        if (newOptions.equals(this.options)) return this;
        return new PlayerVariadicParameter(this, newOptions);
    }

    @Override
    public Seq<Player> parseMultiple(MessageService messageService, String value) throws InvalidParameterException {
        String[] parts = value.split(" ");
        Seq<Player> players = new Seq<>(parts.length);
        for (String part : parts) {
            Player p = parse(messageService, part);
            if (p == null) {
                return null;
            }

            players.add(p);
        }
        return players;
    }
}

package inside.commands.params;

import arc.util.Strings;
import inside.commands.MessageService;
import inside.commands.params.keys.ParameterKey;
import inside.commands.params.keys.SingleKey;
import mindustry.gen.Groups;
import mindustry.gen.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class PlayerParameter extends BaseParameter<Player> {

    protected final Set<SearchOption> options;

    protected PlayerParameter(SingleKey<Player> key) {
        super(key);
        this.options = Set.of();
    }

    protected PlayerParameter(PlayerParameter copy, Set<SearchOption> options) {
        super(copy);
        this.options = options;
    }

    public static PlayerParameter from(SingleKey<Player> key) {
        return new PlayerParameter(key);
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
    public Player parse(MessageService service, String value) {
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

        service.sendError(service.error("player-not-found"), value);
        return null;
    }

    @Override
    public String toString() {
        return "PlayerParameter{" +
                "options=" + options +
                ", name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                "} " + super.toString();
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

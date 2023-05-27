package inside.commands.util;

import arc.files.Fi;
import arc.func.Boolf2;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Structs;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.maps.Map;

import java.util.*;

import static mindustry.Vars.*;

public class Search {

    public static Seq<Player> players(String input, Set<SearchOption> options) {
        int id = parseId(input);

        return Groups.player.copy(new Seq<>(Groups.player.size())).filter(player -> {
            if (options.contains(SearchOption.USE_ID) && player.id == id)
                return true;

            if (options.contains(SearchOption.USE_UUID) && player.uuid().equals(input))
                return true;

            if (options.contains(SearchOption.USE_IP) && player.ip().equals(input))
                return true;

            return deepEquals(player.name, input, options);
        });
    }

    public static Seq<Map> maps(String input, Set<SearchOption> options) {
        // subtract one because map IDs are displayed as (1, 2, 3...)
        int id = parseId(input) - 1;

        return findInSeq(maps.all(), (index, map) -> {
            // skip custom maps if needed
            if (!options.contains(SearchOption.CUSTOM_MAPS) && map.custom)
                return false;

            // skip built-in maps if needed
            if (!options.contains(SearchOption.BUILTIN_MAPS) && !map.custom)
                return false;

            if (options.contains(SearchOption.USE_ID) && index == id)
                return true;

            return deepEquals(map.name(), input, options);
        });
    }

    public static Seq<Fi> saves(String input, Set<SearchOption> options) {
        // subtract one because save IDs are displayed as (1, 2, 3...)
        int id = parseId(input) - 1;

        return findInSeq(saveDirectory.seq().filter(save -> save.extEquals(mapExtension)), (index, save) -> {
            if (options.contains(SearchOption.USE_ID) && index == id)
                return true;

            return deepEquals(save.name(), input, options);
        });
    }

    public static Team team(String input, Set<SearchOption> options) {
        if (options.contains(SearchOption.USE_ID)) {
            int id = parseId(input);
            if (id >= 0 && id < 256)
                return Team.get(id);
        }

        return Structs.find(Team.all, team -> team.name.equals(options.contains(SearchOption.IGNORE_CASE) ? input.toLowerCase() : input));
    }

    public static <T extends UnlockableContent> T content(String input, ContentType type, Set<SearchOption> options) {
        if (options.contains(SearchOption.USE_ID)) {
            T result = content.getByID(type, parseId(input));
            if (result != null)
                return result;
        }

        return content.getByName(type, options.contains(SearchOption.IGNORE_CASE) ? input.toLowerCase() : input);
    }

    // region utils

    public static <T> Seq<T> findInSeq(Seq<T> values, Boolf2<Integer, T> filter) {
        var result = new Seq<T>(values.size);

        for (int i = 0; i < values.size; i++) {
            if (filter.get(i, values.get(i)))
                result.add(values.get(i));
        }

        return result;
    }

    public static <T extends Enum<T>> Seq<T> findInEnum(T[] values, Boolf2<Integer, T> filter) {
        var result = new Seq<T>(values.length);

        for (int i = 0; i < values.length; i++) {
            if (filter.get(i, values[i]))
                result.add(values[i]);
        }

        return result;
    }

    public static int parseId(String input) {
        return input.startsWith("#") ?
                Strings.parseInt(input.substring(1)) :
                Strings.parseInt(input);
    }

    public static boolean deepEquals(String name, String input, Set<SearchOption> options) {
        if (options.contains(SearchOption.IGNORE_CASE)) {
            name = name.toLowerCase();
            input = input.toLowerCase();
        }

        if (options.contains(SearchOption.IGNORE_COLORS)) {
            name = Strings.stripColors(name);
            input = Strings.stripColors(input);
        }

        if (options.contains(SearchOption.IGNORE_EMOJI)) {
            name = Strings.stripGlyphs(name);
            input = Strings.stripGlyphs(input);
        }

        return options.contains(SearchOption.USE_CONTAINS) ? name.contains(input) : name.equals(input);
    }

    // endregion
}
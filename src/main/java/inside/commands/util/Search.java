package inside.commands.util;

import arc.files.Fi;
import arc.struct.ObjectMap.Values;
import arc.struct.Seq;
import arc.util.Reflect;
import arc.util.Strings;
import arc.util.Structs;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.maps.Map;
import mindustry.net.Administration.PlayerInfo;

import java.util.Set;

import static mindustry.Vars.*;

public class Search {

    public static Seq<Player> players(String input, Set<SearchOption> options) {
        var result = new Seq<Player>();
        int id = parseId(input);

        for (var player : Groups.player) {
            if ((options.contains(SearchOption.USE_ID) && player.id == id)
                    || (options.contains(SearchOption.USE_UUID) && player.uuid().equals(input))
                    || (options.contains(SearchOption.USE_IP) && player.ip().equals(input))
                    || deepEquals(player.name, input, options)
            ) result.add(player);
        }

        return result;
    }

    public static Seq<PlayerInfo> playerInfo(String input, Set<SearchOption> options) {
        var playerInfo = availablePlayerInfo();
        var result = new Seq<PlayerInfo>();

        for (var info : playerInfo) {
            if ((options.contains(SearchOption.USE_UUID) && info.id.equals(input))
                    || (options.contains(SearchOption.USE_IP) && info.lastIP.equals(input))
                    || deepEquals(info.lastName, input, options)
            ) result.add(info);
        }

        return result;
    }

    public static Seq<Map> maps(String input, Set<SearchOption> options) {
        var maps = availableMaps();

        var result = new Seq<Map>();
        int id = parseId(input) - 1; // subtract one because map IDs are displayed as (1, 2, 3...)

        for (int i = 0; i < maps.size; i++) {
            var map = maps.get(i);
            if (i == id || deepEquals(map.name(), input, options))
                result.add(map);
        }

        return result;
    }

    public static Seq<Fi> saves(String input, Set<SearchOption> options) {
        var saves = availableSaves();

        var result = new Seq<Fi>();
        int id = parseId(input) - 1; // subtract one because save IDs are displayed as (1, 2, 3...)

        for (int i = 0; i < saves.size; i++) {
            var save = saves.get(i);
            if (i == id || deepEquals(save.name(), input, options))
                result.add(save);
        }

        return result;
    }

    public static Team team(String input, Set<SearchOption> options) {
        if (options.contains(SearchOption.USE_ID)) {
            int id = parseId(input);
            if (id >= 0 && id <= 256)
                return Team.get(id);
        }

        return Structs.find(Team.all, team -> team.name.equals(input.toLowerCase()));
    }

    public static <T extends UnlockableContent> T content(String input, ContentType type, Set<SearchOption> options) {
        if (options.contains(SearchOption.USE_ID)) {
            T result = content.getByID(type, parseId(input));
            if (result != null)
                return result;
        }

        return content.getByName(type, input.toLowerCase());
    }

    // region utils

    public static Iterable<PlayerInfo> availablePlayerInfo() {
        return new Values<>(Reflect.get(netServer.admins, "playerInfo"));
    }

    public static Seq<Map> availableMaps() {
        return maps.customMaps().any() ? maps.customMaps() : maps.defaultMaps();
    }

    public static Seq<Fi> availableSaves() {
        return saveDirectory.seq().filter(save -> save.extEquals(mapExtension));
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
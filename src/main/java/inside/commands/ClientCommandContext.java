package inside.commands;

import arc.struct.ObjectMap;
import mindustry.gen.Player;

import java.util.Locale;

public final class ClientCommandContext extends CommandContext {

    private final Player player;

    ClientCommandContext(Locale locale, BundleProvider bundleProvider,
                         ObjectMap<String, ?> parameters, Player player) {
        super(locale, bundleProvider, parameters);
        this.player = player;
    }

    public Player player() {
        return player;
    }
}

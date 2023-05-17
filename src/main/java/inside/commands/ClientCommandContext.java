package inside.commands;

import arc.struct.ObjectMap;
import mindustry.gen.Player;

import java.util.Locale;

public final class ClientCommandContext extends CommandContext {

    private final Player player;

    ClientCommandContext(Locale locale, BundleProvider bundleProvider,
                         ObjectMap<String, ?> parameters, Player player,
                         ClientMessageService messageService) {
        super(locale, bundleProvider, parameters, messageService);
        this.player = player;
    }

    @Override
    public ClientMessageService messageService() {
        return (ClientMessageService) super.messageService();
    }

    public Player player() {
        return player;
    }
}

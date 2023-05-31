package inside.commands;

import arc.struct.Seq;
import inside.commands.params.Parameter;
import mindustry.gen.Player;

public sealed interface ClientCommandInfo extends CommandInfo {

    boolean admin();

    default String localiseParams(CommandManager manager, Player player) {
        String key = manager.bundleProvider.commandsPrefix() + "." + name() + ".params";
        return manager.bundleProvider.get(key, manager.bundleProvider.getLocale(player));
    }

    default String localiseDescription(CommandManager manager, Player player) {
        String key = manager.bundleProvider.commandsPrefix() + "." + name() + ".description";
        return manager.bundleProvider.get(key, manager.bundleProvider.getLocale(player));
    }
}

record ClientCommandInfoImpl(String name, String paramText, String description, boolean admin, boolean alias,
                             Seq<String> aliases, Seq<Parameter<?>> parameters) implements ClientCommandInfo {}
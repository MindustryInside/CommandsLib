package inside.commands;

import arc.struct.Seq;
import inside.commands.params.Parameter;
import mindustry.gen.Player;

public sealed interface ClientCommandInfo extends CommandInfo {

    boolean admin();

    default String localiseParams(Player player) {
        return "";
    }

    default String localiseDescription(Player player) {
        return "";
    }
}

record ClientCommandInfoImpl(String name, String paramText, String description, boolean admin, boolean alias,
                             Seq<String> aliases, Seq<Parameter<?>> parameters) implements ClientCommandInfo {}
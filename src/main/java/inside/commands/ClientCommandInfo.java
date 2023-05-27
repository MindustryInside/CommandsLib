package inside.commands;

import arc.struct.Seq;
import inside.commands.params.Parameter;

public sealed interface ClientCommandInfo extends CommandInfo {

    boolean admin();
}

record ClientCommandInfoImpl(String name, String paramText, String description, boolean admin, boolean alias,
                             Seq<String> aliases, Seq<Parameter<?>> parameters) implements ClientCommandInfo {}
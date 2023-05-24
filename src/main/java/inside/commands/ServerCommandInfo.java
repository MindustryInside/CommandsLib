package inside.commands;

import arc.func.Cons;
import arc.struct.Seq;
import inside.commands.params.Parameter;

public sealed interface ServerCommandInfo extends CommandInfo {

    @Override
    Cons<ServerCommandContext> handler();
}

record ServerCommandInfoImpl(String name, String description, boolean alias,
                             Seq<String> aliases, Seq<Parameter<?>> parameters,
                             Cons<ServerCommandContext> handler) implements ServerCommandInfo {}
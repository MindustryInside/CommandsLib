package inside.commands;

import arc.func.Cons;
import arc.struct.Seq;
import inside.commands.params.Parameter;

public sealed interface ClientCommandInfo extends CommandInfo {

    @Override
    Cons<ClientCommandContext> handler();

    boolean admin();
}

record ClientCommandInfoImpl(String name, String description, Seq<String> aliases,
                             Seq<Parameter<?>> parameters, Cons<ClientCommandContext> handler,
                             boolean admin) implements ClientCommandInfo {
}

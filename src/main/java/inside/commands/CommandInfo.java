package inside.commands;

import arc.func.Cons;
import arc.struct.Seq;
import inside.commands.params.Parameter;

public sealed interface CommandInfo permits ClientCommandInfo, ServerCommandInfo {

    String name();

    String description();

    Seq<String> aliases();

    Seq<Parameter<?>> parameters();

    Cons<? extends CommandContext> handler();
}

package inside.commands;

import arc.func.Cons;
import arc.struct.Seq;
import inside.commands.params.Parameter;

public sealed interface CommandInfo permits ClientCommandInfo, ServerCommandInfo {

    String name();

    String description();

    // returns is this command an alias
    boolean alias();

    // returns all aliases of this command, null is the command is an alias itself
    Seq<String> aliases();

    Seq<Parameter<?>> parameters();

    Cons<? extends CommandContext> handler();
}
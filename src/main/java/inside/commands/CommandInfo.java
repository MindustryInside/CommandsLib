package inside.commands;

import arc.struct.Seq;
import inside.commands.params.Parameter;

public sealed interface CommandInfo permits ClientCommandInfo, ServerCommandInfo {

    String name();

    String paramText();

    String description();

    boolean alias();

    Seq<String> aliases();

    Seq<Parameter<?>> parameters();
}
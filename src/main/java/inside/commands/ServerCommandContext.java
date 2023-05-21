package inside.commands;

import arc.struct.ObjectMap;

public final class ServerCommandContext extends CommandContext {

    ServerCommandContext(ObjectMap<String, ?> parameters, MessageService messageService) {
        super(parameters, messageService);
    }
}

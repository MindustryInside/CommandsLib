package inside.commands;

import arc.struct.ObjectMap;

import java.util.Locale;

public final class ServerCommandContext extends CommandContext {

    ServerCommandContext(Locale locale, ObjectMap<String, ?> parameters, MessageService messageService) {
        super(locale, parameters, messageService);
    }
}

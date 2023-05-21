package inside.commands;

import arc.struct.ObjectMap;
import mindustry.gen.Player;

public final class ClientCommandContext extends CommandContext {

    public ClientCommandContext(ObjectMap<String, ?> parameters, ClientMessageService messageService) {
        super(parameters, messageService);
    }

    @Override
    public ClientMessageService messageService() {
        return (ClientMessageService) super.messageService();
    }

    public Player player() {
        return messageService().player();
    }
}

package inside.commands;

import mindustry.gen.Player;

public interface ClientMessageService extends MessageService {

    void sendAnnounce(String format, Object... values);
    void sendAnnounce(Player target, String format, Object... values);

    void sendMessage(Player target, String format, Object... values);
}

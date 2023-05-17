package inside.commands;

import mindustry.gen.Player;

public interface ClientMessageService extends MessageService {

    Player player();

    default void sendAnnounce(String format, Object... values) {
        sendAnnounce(player(), format, values);
    }

    void sendAnnounce(Player target, String format, Object... values);

    @Override
    default void sendMessage(String format, Object... values) {
        sendMessage(player(), format, values);
    }

    void sendMessage(Player target, String format, Object... values);
}

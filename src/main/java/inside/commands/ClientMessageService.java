package inside.commands;

import mindustry.gen.Player;

public interface ClientMessageService extends MessageService {

    /** {@return the player for whom messages are sent by default} */
    Player player();

    default void announce(String format, Object... values) {
        announce(player(), format, values);
    }

    void announce(Player target, String format, Object... values);

    @Override
    default void sendMessage(String format, Object... values) {
        sendMessage(player(), format, values);
    }

    void sendMessage(Player target, String format, Object... values);
}

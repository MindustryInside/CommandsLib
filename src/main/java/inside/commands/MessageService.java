package inside.commands;

import mindustry.gen.Player;

import java.util.Locale;

public interface MessageService {

    default String prefix(String key) {
        return bundle().commandsPrefix() + '.' + key;
    }

    Locale locale();

    BundleProvider bundle();

    void sendError(String format, Object... values);

    void sendMessage(String format, Object... values);

    interface Factory {

        ClientMessageService createClient(BundleProvider bundle, Player player);

        MessageService createServer(BundleProvider bundle, Locale locale);
    }
}
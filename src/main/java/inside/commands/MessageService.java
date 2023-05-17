package inside.commands;

import mindustry.gen.Player;

import java.util.Locale;

public interface MessageService {

    String ADMIN_ONLY_COMMAND = "commands.admin-only";

    void sendError(String format, Object... values);

    void sendMessage(String format, Object... values);

    interface Factory {

        ClientMessageService createClient(BundleProvider bundleProvider, Player player);

        MessageService createServer(BundleProvider bundleProvider, Locale locale);
    }
}

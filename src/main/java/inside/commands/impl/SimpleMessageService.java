package inside.commands.impl;

import arc.util.Log;
import inside.commands.BundleProvider;
import inside.commands.ClientMessageService;
import inside.commands.MessageService;
import mindustry.gen.BlockSnapshotCallPacket;
import mindustry.gen.Call;
import mindustry.gen.Player;

import java.util.Locale;
import java.util.Objects;

public class SimpleMessageService implements ClientMessageService {

    public static Factory factory() {
        return new Factory() {
            @Override
            public ClientMessageService createClient(BundleProvider bundleProvider, Player player) {
                Locale locale = bundleProvider.getLocale(player);
                return new SimpleMessageService(bundleProvider, locale, player);
            }

            @Override
            public MessageService createServer(BundleProvider bundleProvider, Locale locale) {
                return new SimpleMessageService(bundleProvider, locale, null);
            }
        };
    }

    private final BundleProvider bundleProvider;
    private final Locale locale;
    private final Player player;

    SimpleMessageService(BundleProvider bundleProvider, Locale locale, Player player) {
        this.bundleProvider = bundleProvider;
        this.locale = locale;
        this.player = player;
    }

    @Override
    public void sendError(String format, Object... values) {
        String bundled = bundleProvider.format(locale, format, values);
        if (player != null) {
            player.sendMessage(bundled);
        } else {
            Log.err(bundled);
        }
    }

    @Override
    public void sendMessage(String format, Object... values) {
        String bundled = bundleProvider.format(locale, format, values);
        if (player != null) {
            player.sendMessage(bundled);
        } else {
            Log.info(bundled);
        }
    }

    // ClientMessageService methods
    // ============================

    @Override
    public void sendAnnounce(String format, Object... values) {
        Objects.requireNonNull(player);

        String bundled = bundleProvider.format(locale, format, values);
        Call.announce(player.con, bundled);
    }

    @Override
    public void sendAnnounce(Player target, String format, Object... values) {
        Locale locale = bundleProvider.getLocale(target);
        String bundled = bundleProvider.format(locale, format, values);
        Call.announce(target.con, bundled);
    }

    @Override
    public void sendMessage(Player target, String format, Object... values) {
        Locale locale = bundleProvider.getLocale(target);
        String bundled = bundleProvider.format(locale, format, values);
        target.sendMessage(bundled);
    }
}

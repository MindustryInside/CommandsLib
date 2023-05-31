package inside.commands.simple;

import arc.util.*;
import inside.commands.BundleProvider;
import inside.commands.ClientMessageService;
import inside.commands.MessageService;
import mindustry.gen.Call;
import mindustry.gen.Player;

import java.util.Locale;

public class SimpleMessageService implements ClientMessageService {

    public static Factory factory() {
        return new Factory() {
            @Override
            public ClientMessageService createClient(BundleProvider bundle, Player player) {
                Locale locale = bundle.getLocale(player);
                return new SimpleMessageService(bundle, locale, player);
            }

            @Override
            public MessageService createServer(BundleProvider bundle, Locale locale) {
                return new SimpleMessageService(bundle, locale, null);
            }
        };
    }

    private final BundleProvider bundle;
    private final Locale locale;
    private final Player player; // null for server commands

    SimpleMessageService(BundleProvider bundle, Locale locale, Player player) {
        this.bundle = bundle;
        this.locale = locale;
        this.player = player;
    }

    @Override
    public Locale locale() {
        return locale;
    }

    @Override
    public BundleProvider bundle() {
        return bundle;
    }

    @Override
    public void sendError(String format, Object... values) {
        String bundled = bundle.format(format, locale, values);
        if (player != null) {
            player.sendMessage(bundled);
        } else {
            Log.err(Strings.stripColors(Strings.stripGlyphs(bundled)));
        }
    }

    @Override
    public void sendMessage(String format, Object... values) {
        String bundled = bundle.format(format, locale, values);
        if (player != null) {
            player.sendMessage(bundled);
        } else {
            Log.info(Strings.stripColors(Strings.stripGlyphs(bundled)));
        }
    }

    // ClientMessageService methods
    // ============================

    @Override
    public Player player() {
        return player;
    }

    @Override
    public void announce(Player target, String format, Object... values) {
        Locale locale = bundle.getLocale(target);
        String bundled = bundle.format(format, locale, values);
        Call.announce(target.con, bundled);
    }

    @Override
    public void sendMessage(Player target, String format, Object... values) {
        Locale locale = bundle.getLocale(target);
        String bundled = bundle.format(format, locale, values);
        target.sendMessage(bundled);
    }
}
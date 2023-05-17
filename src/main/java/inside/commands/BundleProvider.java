package inside.commands;

import mindustry.gen.Player;

import java.util.Locale;

public interface BundleProvider {

    Locale getLocale(Player player);

    String get(Locale locale, String key);

    String format(Locale locale, String key, Object... values);
}

package inside.commands;

import mindustry.gen.Player;

import java.util.Locale;

public interface BundleProvider {

    Locale getLocale(Player player);

    String get(String key, Locale locale);

    String format(String key, Locale locale, Object... values);
}

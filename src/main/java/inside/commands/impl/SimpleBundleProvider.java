package inside.commands.impl;

import inside.commands.BundleProvider;
import mindustry.gen.Player;

import java.text.MessageFormat;
import java.util.Locale;

public enum SimpleBundleProvider implements BundleProvider {
    INSTANCE;

    @Override
    public Locale getLocale(Player player) {
        return Locale.ROOT;
    }

    @Override
    public String get(Locale locale, String key) {
        return key;
    }

    @Override
    public String format(Locale locale, String key, Object... values) {
        MessageFormat fmt = new MessageFormat(key, locale);
        return fmt.format(values);
    }
}

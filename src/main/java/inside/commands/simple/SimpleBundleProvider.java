package inside.commands.simple;

import inside.commands.BundleProvider;
import mindustry.gen.Player;

import java.text.MessageFormat;
import java.util.Locale;

public enum SimpleBundleProvider implements BundleProvider {
    INSTANCE;

    @Override
    public Locale getLocale(Player player) {
        return Locale.ENGLISH;
    }

    @Override
    public String get(String key, Locale locale) {
        return key;
    }

    @Override
    public String format(String key, Locale locale, Object... values) {
        MessageFormat format = new MessageFormat(key, locale);
        return format.format(values);
    }
}
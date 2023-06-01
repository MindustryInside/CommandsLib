package inside.commands;

import arc.struct.Seq;
import inside.commands.params.Parameter;
import mindustry.gen.Player;

import java.util.Locale;

public sealed interface ClientCommandInfo extends CommandInfo {

    boolean admin();

    default String localiseParams(BundleProvider bundleProvider, Player player) {
        return localiseParams(bundleProvider, bundleProvider.getLocale(player));
    }

    default String localiseDescription(BundleProvider bundleProvider, Player player) {
        return localiseDescription(bundleProvider, bundleProvider.getLocale(player));
    }

    default String localiseParams(BundleProvider bundleProvider, Locale locale) {
        String key = bundleProvider.commandsPrefix() + "." + name() + ".params";
        return bundleProvider.get(key, locale);
    }

    default String localiseDescription(BundleProvider bundleProvider, Locale locale) {
        String key = bundleProvider.commandsPrefix() + "." + name() + ".description";
        return bundleProvider.get(key, locale);
    }
}

record ClientCommandInfoImpl(String name, String paramText, String description, boolean admin, boolean alias,
                             Seq<String> aliases, Seq<Parameter<?>> parameters) implements ClientCommandInfo {}
package inside.commands;

import arc.util.CommandHandler;

import java.util.Locale;
import java.util.Objects;

public final class CommandManager {

     final CommandHandler handler;

     Locale locale = Locale.ROOT;
     BundleProvider bundleProvider;

     public CommandManager(CommandHandler handler) {
          this.handler = handler;
     }

     public CommandManager setConsoleLocale(Locale locale) {
          this.locale = Objects.requireNonNull(locale);
          return this;
     }

     public CommandManager setBundleProvider(BundleProvider bundleProvider) {
          this.bundleProvider = Objects.requireNonNull(bundleProvider);
          return this;
     }

     public CommandBuilder register(String name) {
          return new CommandBuilder(this, name);
     }
}

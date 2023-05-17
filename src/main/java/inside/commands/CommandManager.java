package inside.commands;

import arc.util.CommandHandler;

import java.util.Locale;
import java.util.Objects;

public final class CommandManager {

     final CommandHandler serverHandler;
     final CommandHandler clientHandler;

     Locale consoleLocale = Locale.ROOT;
     BundleProvider bundleProvider;

     public CommandManager(CommandHandler serverHandler, CommandHandler clientHandler) {
          this.serverHandler = Objects.requireNonNull(serverHandler);
          this.clientHandler = Objects.requireNonNull(clientHandler);
     }

     public CommandManager setConsoleLocale(Locale locale) {
          this.consoleLocale = Objects.requireNonNull(locale);
          return this;
     }

     public CommandManager setBundleProvider(BundleProvider bundleProvider) {
          this.bundleProvider = Objects.requireNonNull(bundleProvider);
          return this;
     }

     public ClientCommandBuilder registerClient(String name) {
          return new ClientCommandBuilder(this, name);
     }

     public ServerCommandBuilder registerServer(String name) {
          return new ServerCommandBuilder(this, name);
     }
}

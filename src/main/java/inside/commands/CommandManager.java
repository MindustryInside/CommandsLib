package inside.commands;

import arc.util.CommandHandler;
import inside.commands.simple.SimpleBundleProvider;
import inside.commands.simple.SimpleMessageService;

import java.util.Locale;
import java.util.Objects;

public final class CommandManager {

     CommandHandler serverHandler;
     CommandHandler clientHandler;

     MessageService.Factory messageServiceFactory = SimpleMessageService.factory();
     Locale consoleLocale = Locale.ROOT;
     BundleProvider bundleProvider = SimpleBundleProvider.INSTANCE;

     public CommandManager() {}

     public CommandManager(CommandHandler serverHandler, CommandHandler clientHandler) {
          this.serverHandler = Objects.requireNonNull(serverHandler);
          this.clientHandler = Objects.requireNonNull(clientHandler);
     }

     public void setServerHandler(CommandHandler serverHandler) {
          if (this.serverHandler != null) {
               throw new IllegalStateException("Server handler cant be replaced");
          }

          this.serverHandler = Objects.requireNonNull(serverHandler);
     }

     public void setClientHandler(CommandHandler clientHandler) {
          if (this.clientHandler != null) {
               throw new IllegalStateException("Client handler cant be replaced");
          }

          this.clientHandler = Objects.requireNonNull(clientHandler);
     }

     public CommandManager setMessageServiceFactory(MessageService.Factory factory) {
          this.messageServiceFactory = Objects.requireNonNull(factory);
          return this;
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
          if (clientHandler == null)
               throw new IllegalStateException("Client handler not specified");
          return new ClientCommandBuilder(this, name);
     }

     public ServerCommandBuilder registerServer(String name) {
          if (serverHandler == null)
               throw new IllegalStateException("Server handler not specified");
          return new ServerCommandBuilder(this, name);
     }
}

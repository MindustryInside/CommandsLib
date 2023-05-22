package inside.commands;

import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import inside.commands.simple.SimpleBundleProvider;
import inside.commands.simple.SimpleMessageService;
import mindustry.ui.Menus;

import java.util.Locale;
import java.util.Objects;

import static mindustry.Vars.netServer;
import static mindustry.server.ServerControl.instance;

public final class CommandManager {

     int parameterMenuId;
     final ObjectMap<String, MenuContext> menuContexts = new ObjectMap<>();

     final ObjectMap<String, CommandInfo> commands = new ObjectMap<>();

     CommandHandler serverHandler;
     CommandHandler clientHandler;

     MessageService.Factory messageServiceFactory = SimpleMessageService.factory();
     Locale consoleLocale = Locale.ROOT;
     BundleProvider bundleProvider = SimpleBundleProvider.INSTANCE;

     public CommandManager() {
          this(instance.handler, netServer.clientCommands);
     }

     public CommandManager(CommandHandler serverHandler, CommandHandler clientHandler) {
          this.setServerHandler(serverHandler);
          this.setClientHandler(clientHandler);
     }

     public void setServerHandler(CommandHandler serverHandler) {
          if (this.serverHandler != null) {
               throw new IllegalStateException("Server handler cant be replaced");
          }

          this.serverHandler = Objects.requireNonNull(serverHandler);
     }

     private void initializeMenuSupport() {

          parameterMenuId = Menus.registerMenu((player, option) -> {
               if (option == -1) {
                    player.sendMessage("Ну и зачем надо было закрывать меню?");
                    menuContexts.remove(player.uuid());
                    return;
               }

               var ctx = menuContexts.get(player.uuid());
               ctx.onClick(option);
          });

          var original = netServer.invalidHandler;
          netServer.invalidHandler = (player, response) -> {
               if (response.type == CommandHandler.ResponseType.fewArguments) {
                    var commandInfo = commands.get(response.command.text);
                    if (commandInfo instanceof ClientCommandInfo c) {
                         // TODO ???
                         // if (c.admin() && !player.admin) {
                         //      return null;
                         // }
                         var messageService = messageServiceFactory.createClient(bundleProvider, player);

                         MenuContext ctx = new MenuContext(this, messageService, c);
                         menuContexts.put(player.uuid(), ctx);

                         ctx.createMenu();
                         return null;
                    }
               }

               return original.handle(player, response);
          };
     }

     public void setClientHandler(CommandHandler clientHandler) {
          if (this.clientHandler != null) {
               throw new IllegalStateException("Client handler cant be replaced");
          }

          this.clientHandler = Objects.requireNonNull(clientHandler);
          initializeMenuSupport();
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
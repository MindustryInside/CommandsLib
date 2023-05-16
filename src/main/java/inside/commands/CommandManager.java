package inside.commands;

import arc.util.CommandHandler;

public final class CommandManager {

     final CommandHandler handler;

     public CommandManager(CommandHandler handler) {
          this.handler = handler;
     }

     public CommandBuilder register(String name) {
          return new CommandBuilder(name, handler);
     }
}

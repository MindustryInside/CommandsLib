package inside.commands;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.CommandHandler;
import inside.commands.params.Parameter;
import inside.commands.params.ParameterWithDefaultValue;
import mindustry.gen.Player;

public final class ServerCommandBuilder extends CommandBuilder {
    ServerCommandBuilder(CommandManager manager, String name) {
        super(manager, name);
    }

    @Override
    public ServerCommandBuilder aliases(String... aliases) {
        return (ServerCommandBuilder) super.aliases(aliases);
    }

    @Override
    public ServerCommandBuilder aliases(Iterable<String> aliases) {
        return (ServerCommandBuilder) super.aliases(aliases);
    }

    @Override
    public ServerCommandBuilder description(String description) {
        return (ServerCommandBuilder) super.description(description);
    }

    @Override
    public ServerCommandBuilder parameter(Parameter<?> param) {
        return (ServerCommandBuilder) super.parameter(param);
    }

    public void handler(Cons<ServerCommandContext> handler) {
        String paramText = parameters.toString(" ", CommandBuilder::parameterAsString);
        CommandHandler.CommandRunner<Player> runner = (args, player) -> run(handler, args);

        var commandInfo = new ServerCommandInfoImpl(name, description, new Seq<>(aliases),
                new Seq<>(parameters), handler);
        manager.commands.put(name, commandInfo);
        manager.serverHandler.register(name, paramText, description, runner);
        if (aliases != null) {
            for (String alias : aliases) {
                manager.commands.put(name, commandInfo);
                manager.serverHandler.register(alias, paramText, description, runner);
            }
        }
    }

    private void run(Cons<ServerCommandContext> handler, String[] args) {
        MessageService messageService = manager.messageServiceFactory.createServer(manager.bundleProvider, manager.consoleLocale);
        var parsedParams = new ObjectMap<String, Object>();
        int i = 0;
        for (; i < args.length; i++) {
            var p = parameters.get(i);
            Object parsed = p.parse(messageService, args[i]);
            if (parsed == null) {
                return;
            }

            parsedParams.put(p.name(), parsed);
        }

        for (; i < parameters.size; i++) {
            var p = parameters.get(i);
            if (p instanceof ParameterWithDefaultValue<?> d) {
                parsedParams.put(p.name(), d.getDefault());
            }
        }

        handler.get(new ServerCommandContext(parsedParams, messageService));
    }
}

package inside.commands;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.CommandHandler.CommandRunner;
import inside.commands.params.Parameter;
import inside.commands.params.DefaultValueParameter;

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
        CommandRunner<?> runner = (args, object) -> run(handler, args);

        var commandInfo = new ServerCommandInfoImpl(name, paramText, description, false, aliases.copy(), parameters.copy());
        var mainDesc = new ServerCommandDescriptor(commandInfo, handler);
        var aliasInfo = new ServerCommandInfoImpl(name, paramText, description, true, Seq.with(), parameters.copy());
        var aliasDesc = new ServerCommandDescriptor(aliasInfo, handler);

        manager.serverCommands.put(name, mainDesc);
        manager.serverHandler.register(name, paramText, description, runner);

        for (String alias : aliases) {
            manager.serverCommands.put(alias, aliasDesc);
            manager.serverHandler.register(alias, paramText, description, runner);
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
            if (p instanceof DefaultValueParameter<?> d) {
                parsedParams.put(p.name(), d.getDefault());
            }
        }

        handler.get(new ServerCommandContext(parsedParams, messageService));
    }
}
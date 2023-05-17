package inside.commands;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import inside.commands.params.Parameter;
import inside.commands.params.VariadicParameter;
import mindustry.gen.Player;

import java.util.StringJoiner;

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
        StringJoiner paramSj = new StringJoiner(" ");
        for (var p : parameters) {
            paramSj.add(parameterAsString(p));
        }

        String paramString = paramSj.toString();

        CommandHandler.CommandRunner<Player> runner = (args, player) -> run(handler, args);
        manager.serverHandler.register(name, paramString, description, runner);
        if (aliases != null) {
            for (String alias : aliases) {
                manager.serverHandler.register(alias, paramString, description, runner);
            }
        }
    }

    private void run(Cons<ServerCommandContext> handler, String[] args) {
        MessageService messageService = manager.messageServiceFactory.createServer(manager.bundleProvider, manager.consoleLocale);
        var parsedParams = new ObjectMap<String, Object>();
        for (int i = 0; i < args.length; i++) {
            var p = parameters.get(i);
            Object parsed = p instanceof VariadicParameter<?> v
                    ? v.parseMultiple(messageService, args[i])
                    : p.parse(messageService, args[i]);

            if (parsed == null) {
                return;
            }

            parsedParams.put(p.name(), parsed);
        }

        handler.get(new ServerCommandContext(manager.consoleLocale, parsedParams, messageService));
    }
}

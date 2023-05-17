package inside.commands;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.params.InvalidParameterException;
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
            if (p instanceof VariadicParameter<?> v) {
                try {
                    parsedParams.put(p.name(), v.parseMultiple(args[i]));
                } catch (InvalidParameterException e) {
                    e.report(messageService);
                    return;
                }
            } else {
                try {
                    parsedParams.put(p.name(), p.parse(args[i]));
                } catch (InvalidParameterException e) {
                    e.report(messageService);
                    return;
                }
            }
        }

        handler.get(new ServerCommandContext(manager.consoleLocale,
                manager.bundleProvider, parsedParams, messageService));
    }
}

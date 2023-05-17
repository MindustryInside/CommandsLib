package inside.commands;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.params.InvalidParameterException;
import inside.commands.params.Parameter;
import inside.commands.params.VariadicParameter;
import mindustry.gen.Player;

import java.util.Locale;
import java.util.StringJoiner;

public final class ClientCommandBuilder extends CommandBuilder {

    ClientCommandBuilder(CommandManager manager, String name) {
        super(manager, name);
    }

    @Override
    public ClientCommandBuilder aliases(String... aliases) {
        return (ClientCommandBuilder) super.aliases(aliases);
    }

    @Override
    public ClientCommandBuilder aliases(Iterable<String> aliases) {
        return (ClientCommandBuilder) super.aliases(aliases);
    }

    @Override
    public ClientCommandBuilder description(String description) {
        return (ClientCommandBuilder) super.description(description);
    }

    @Override
    public ClientCommandBuilder parameter(Parameter<?> param) {
        return (ClientCommandBuilder) super.parameter(param);
    }

    public void handler(Cons<ClientCommandContext> handler) {
        StringJoiner paramSj = new StringJoiner(" ");
        for (var p : parameters) {
            paramSj.add(parameterAsString(p));
        }

        String paramString = paramSj.toString();

        CommandHandler.CommandRunner<Player> runner = (args, player) -> run(handler, args, player);
        manager.clientHandler.register(name, paramString, description, runner);
        if (aliases != null) {
            for (String alias : aliases) {
                manager.serverHandler.register(alias, paramString, description, runner);
            }
        }
    }

    private void run(Cons<ClientCommandContext> handler, String[] args, Player player) {
        Locale locale = manager.bundleProvider.getLocale(player);
        var parsedParams = new ObjectMap<String, Object>();
        for (int i = 0; i < args.length; i++) {
            var p = parameters.get(i);
            if (p instanceof VariadicParameter<?> v) {
                try {
                    parsedParams.put(p.name(), v.parseMultiple(args[i]));
                } catch (InvalidParameterException e) {
                    String msg = e.localise(locale);

                    Log.err(msg);
                    return;
                }
            } else {
                try {
                    parsedParams.put(p.name(), p.parse(args[i]));
                } catch (InvalidParameterException e) {
                    String msg = e.localise(locale);

                    Log.err(msg);
                    return;
                }
            }
        }

        handler.get(new ClientCommandContext(locale, manager.bundleProvider, parsedParams, player));
    }
}

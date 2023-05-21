package inside.commands;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import inside.commands.params.Parameter;
import inside.commands.params.ParameterWithDefaultValue;
import mindustry.gen.Player;

import java.util.Locale;
import java.util.StringJoiner;

public final class ClientCommandBuilder extends CommandBuilder {

    boolean admin;

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

    public ClientCommandBuilder admin(boolean admin) {
        this.admin = admin;
        return this;
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
                manager.clientHandler.register(alias, paramString, description, runner);
            }
        }
    }

    private void run(Cons<ClientCommandContext> handler, String[] args, Player player) {
        if (player == null) {
            throw new IllegalStateException("'player' must be present for client commands");
        }

        Locale locale = manager.bundleProvider.getLocale(player);
        ClientMessageService messageService = manager.messageServiceFactory.createClient(manager.bundleProvider, player);
        if (admin && !player.admin) {
            messageService.sendError(messageService.error("admin-only"));
            return;
        }

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

        handler.get(new ClientCommandContext(locale, parsedParams, player, messageService));
    }
}

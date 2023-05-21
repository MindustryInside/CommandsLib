package inside.commands;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.CommandHandler;
import inside.commands.params.Parameter;
import inside.commands.params.ParameterWithDefaultValue;
import mindustry.gen.Player;

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
        String paramText = parameters.toString(" ", CommandBuilder::parameterAsString);
        CommandHandler.CommandRunner<Player> runner = (args, player) -> run(handler, args, player);

        var commandInfo = new ClientCommandInfoImpl(name, description, new Seq<>(aliases),
                new Seq<>(parameters), handler, admin);
        manager.commands.put(name, commandInfo);

        manager.clientHandler.register(name, paramText, description, runner);
        if (aliases != null) {
            for (String alias : aliases) {
                manager.commands.put(alias, commandInfo);
                manager.clientHandler.register(alias, paramText, description, runner);
            }
        }
    }

    private void run(Cons<ClientCommandContext> handler, String[] args, Player player) {
        if (player == null) {
            throw new IllegalStateException("'player' must be present for client commands");
        }

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

        handler.get(new ClientCommandContext(parsedParams, messageService));
    }
}

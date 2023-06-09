package inside.commands;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.CommandHandler.CommandRunner;
import inside.commands.params.Parameter;
import inside.commands.params.DefaultValueParameter;
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
        CommandRunner<Player> runner = (args, player) -> run(handler, args, player);

        var commandInfo = new ClientCommandInfoImpl(name, paramText, description, admin, false, aliases.copy(), parameters.copy());
        var mainDesc = new ClientCommandDescriptor(commandInfo, handler);
        var aliasInfo = new ClientCommandInfoImpl(name, paramText, description, admin, true, Seq.with(), parameters.copy());
        var aliasDesc = new ClientCommandDescriptor(aliasInfo, handler);

        manager.clientCommands.put(name, mainDesc);
        manager.clientHandler.register(name, paramText, description, runner);

        for (String alias : aliases) {
            manager.clientCommands.put(alias, aliasDesc);
            manager.clientHandler.register(alias, paramText, description, runner);
        }
    }

    private void run(Cons<ClientCommandContext> handler, String[] args, Player player) {
        if (player == null) {
            throw new IllegalStateException("'player' must be present for client commands");
        }

        ClientMessageService service = manager.messageServiceFactory.createClient(manager.bundleProvider, player);
        if (admin && !player.admin) {
            service.sendError(service.prefix("admin-only"));
            return;
        }

        var parsedParams = new ObjectMap<String, Object>();
        int i = 0;
        for (; i < args.length; i++) {
            var p = parameters.get(i);
            Object parsed = p.parse(service, args[i]);
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

        handler.get(new ClientCommandContext(parsedParams, service));
    }
}
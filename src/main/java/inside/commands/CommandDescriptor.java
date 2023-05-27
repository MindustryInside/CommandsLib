package inside.commands;

import arc.func.Cons;

sealed interface CommandDescriptor {

    CommandInfo info();

    Cons<? extends CommandContext> handler();
}

record ClientCommandDescriptor(ClientCommandInfo info, Cons<ClientCommandContext> handler) implements CommandDescriptor {
    @Override
    public ClientCommandInfo info() {
        return info;
    }

    @Override
    public Cons<ClientCommandContext> handler() {
        return handler;
    }
}

record ServerCommandDescriptor(ServerCommandInfo info, Cons<ServerCommandContext> handler) implements CommandDescriptor {
    @Override
    public ServerCommandInfo info() {
        return info;
    }

    @Override
    public Cons<ServerCommandContext> handler() {
        return handler;
    }
}

package inside.commands;

import arc.func.*;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.CommandHandler;
import inside.commands.simple.SimpleBundleProvider;
import inside.commands.simple.SimpleMessageService;
import inside.commands.MessageService.Factory;
import mindustry.gen.Player;
import mindustry.ui.Menus;

import java.util.Locale;
import java.util.Objects;

import static mindustry.Vars.netServer;
import static mindustry.server.ServerControl.instance;

public final class CommandManager {

    int parameterMenuId;
    int parameterTextInputMenuId;
    final ObjectMap<String, MenuContext> menuContexts = new ObjectMap<>();

    final ObjectMap<String, ClientCommandDescriptor> clientCommands = new ObjectMap<>();
    final ObjectMap<String, ServerCommandDescriptor> serverCommands = new ObjectMap<>();

    CommandHandler clientHandler;
    CommandHandler serverHandler;

    Factory messageServiceFactory = SimpleMessageService.factory();

    Locale consoleLocale = Locale.ENGLISH;
    BundleProvider bundleProvider = SimpleBundleProvider.INSTANCE;

    public CommandManager() {
        // TODO небезопасная инициализация если создавать в конструкторе плагина
        this(netServer.clientCommands, instance.handler);
    }

    public CommandManager(CommandHandler clientHandler, CommandHandler serverHandler) {
        this.setClientHandler(clientHandler);
        this.setServerHandler(serverHandler);
    }

    private void initializeMenuSupport() {
        parameterMenuId = Menus.registerMenu((player, option) -> {
            if (option < 0) {
                menuContexts.remove(player.uuid());
                return;
            }

            var context = menuContexts.get(player.uuid());
            context.onClick(option);
        });

        var original = netServer.invalidHandler;
        netServer.invalidHandler = (player, response) -> {
            if (response.type == CommandHandler.ResponseType.fewArguments) {
                var commandInfo = clientCommands.get(response.command.text);
                // TODO ???
                // if (c.admin() && !player.admin) {
                //      return null;
                // }
                var messageService = messageServiceFactory.createClient(bundleProvider, player);

                MenuContext ctx = new MenuContext(this, messageService, commandInfo);
                menuContexts.put(player.uuid(), ctx);

                ctx.createMenu();
                return null;
            }

            return original.handle(player, response);
        };
    }

    private void initializeTextInputSupport() {
        parameterTextInputMenuId = Menus.registerTextInput((player, text) -> {
            if (text == null) {
                menuContexts.remove(player.uuid());
                return;
            }

            var context = menuContexts.get(player.uuid());
            context.onTextInput(text);
        });
    }

    public CommandManager setClientHandler(CommandHandler clientHandler) {
        if (this.clientHandler != null) {
            throw new IllegalStateException("Client handler cannot be replaced");
        }

        this.clientHandler = Objects.requireNonNull(clientHandler);

        initializeMenuSupport();
        initializeTextInputSupport();

        return this;
    }

    public CommandManager setServerHandler(CommandHandler serverHandler) {
        if (this.serverHandler != null) {
            throw new IllegalStateException("Server handler cannot be replaced");
        }

        this.serverHandler = Objects.requireNonNull(serverHandler);
        return this;
    }

    public CommandManager setMessageServiceFactory(Factory factory) {
        this.messageServiceFactory = Objects.requireNonNull(factory);
        return this;
    }

    public CommandManager setConsoleLocale(String locale) {
        return setConsoleLocale(new Locale(locale));
    }

    public CommandManager setConsoleLocale(Locale locale) {
        this.consoleLocale = Objects.requireNonNull(locale);
        return this;
    }

    public CommandManager setBundleProvider(Func<Player, Locale> getLocale, Func2<String, Locale, String> get, Func3<String, Locale, Object[], String> format) {
        return setBundleProvider(new BundleProvider() {
            @Override
            public Locale getLocale(Player player) {
                return getLocale.get(player);
            }

            @Override
            public String get(String key, Locale locale) {
                return get.get(key, locale);
            }

            @Override
            public String format(String key, Locale locale, Object... values) {
                return format.get(key, locale, values);
            }
        });
    }

    public CommandManager setBundleProvider(BundleProvider bundleProvider) {
        this.bundleProvider = Objects.requireNonNull(bundleProvider);
        return this;
    }

    public ClientCommandBuilder registerClient(String name) {
        if (clientHandler == null)
            throw new IllegalStateException("Client handler not specified");
        return new ClientCommandBuilder(this, name);
    }

    public ServerCommandBuilder registerServer(String name) {
        if (serverHandler == null)
            throw new IllegalStateException("Server handler not specified");
        return new ServerCommandBuilder(this, name);
    }

    public Seq<ClientCommandInfo> getClientCommands(boolean includeAdmin) {
        return getClientCommands(includeAdmin, false);
    }

    public Seq<ClientCommandInfo> getClientCommands(boolean includeAdmin, boolean includeAliases) {
        return clientHandler.getCommandList()
                .map(command -> {
                    var desc = clientCommands.get(command.text);
                    if (desc != null)
                        return desc.info();

                    return new ClientCommandInfoImpl(command.text, command.paramText,
                            command.description, false, false, Seq.with(), Seq.with());
                })
                .filter(command -> (includeAdmin || !command.admin()) && (includeAliases || !command.alias()));
    }

    public Seq<ServerCommandInfo> getServerCommands() {
        return getServerCommands(false);
    }

    public Seq<ServerCommandInfo> getServerCommands(boolean includeAliases) {
        return serverHandler.getCommandList()
                .map(command -> {
                    var desc = serverCommands.get(command.text);
                    if (desc != null)
                        return desc.info();

                    return new ServerCommandInfoImpl(command.text, command.paramText,
                            command.description, false, Seq.with(), Seq.with());
                })
                .filter(command -> includeAliases || !command.alias());
    }


    public String localiseParams(ClientCommandInfo command, Player player) {
        return bundleProvider.get( bundleProvider.commandsPrefix() + "." + command.name() + ".params", bundleProvider.getLocale(player));
    }
    public String localiseDescription(ClientCommandInfo command, Player player) {
        return bundleProvider.get( bundleProvider.commandsPrefix() + "." + command.name() + ".description", bundleProvider.getLocale(player));
    }
}
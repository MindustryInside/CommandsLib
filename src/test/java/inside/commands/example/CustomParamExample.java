package inside.commands.example;

import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.CommandManager;
import inside.commands.MessageService;
import inside.commands.params.BaseParameter;
import inside.commands.params.InvalidParameterException;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.ParameterKey;

import java.util.Locale;

class CustomParamExample {
    static final CommandHandler commonHandler = new CommandHandler("/");

    static final MandatoryKey<String> name = MandatoryKey.of("name");

    // map which contains admin names and their role
    static final ObjectMap<String, String> admins = new ObjectMap<>();

    static {
        addAdmin("Skat", "Fish");
        addAdmin("Dark", "Dark");
        addAdmin("Nekonya", "Nya~");
        addAdmin("Ospx", "xpsO");
    }

    static void addAdmin(String name, String role) {
        admins.put(name.toLowerCase(Locale.ROOT), role);
    }

    static String getRole(String name) {
        return admins.get(name.toLowerCase(Locale.ROOT));
    }

    public static void main(String[] args) {
        CommandManager manager = new CommandManager(commonHandler, commonHandler);
        manager.registerClient("get")
                .description("desc")
                .parameter(CustomParameter.from(name))
                .handler(ctx -> {
                    String role = ctx.get(name);
                    Log.info("Found role: '@'", role);
                });

        performCommand("/get skat");
        performCommand("/get anuke");
    }

    static void performCommand(String text) {
        var res = commonHandler.handleMessage(text);
        if (res.type != CommandHandler.ResponseType.valid) {
            Log.info(res.type);
        }
    }

    static class CustomParameter extends BaseParameter<String> {

        protected CustomParameter(ParameterKey<String> key) {
            super(key);
        }

        public static CustomParameter from(ParameterKey<String> key) {
            return new CustomParameter(key);
        }

        @Override
        public String parse(String value) {
            String role = getRole(value);
            if (role == null) {
                throw new AdminNotFoundException(value);
            }
            return role;
        }
    }

    static class AdminNotFoundException extends InvalidParameterException {
        private final String name;

        AdminNotFoundException(String name) {
            this.name = name;
        }

        @Override
        public void report(MessageService messageService) {
            messageService.sendError("Oh no! No admin with name '@' found!", name);
        }
    }
}

package inside.commands.example;

import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.CommandManager;
import inside.commands.MessageService;
import inside.commands.params.BaseParameter;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.MandatorySingleKey;
import inside.commands.params.keys.SingleKey;

import java.util.Locale;

class CustomParamExample {
    static final CommandHandler commonHandler = new CommandHandler("/");

    static final MandatorySingleKey<String> name = MandatoryKey.single("name");

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
        manager.registerServer("get")
                .description("desc")
                .parameter(CustomParameter.from(name))
                .handler(ctx -> {
                    String role = ctx.get(name);
                    ctx.messageService().sendMessage("Found role: {0}", role);
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

        protected CustomParameter(SingleKey<String> key) {
            super(key);
        }

        static CustomParameter from(SingleKey<String> key) {
            return new CustomParameter(key);
        }

        @Override
        public String parse(MessageService service, String value) {
            String role = getRole(value);
            if (role == null) {
                service.sendError(service.error("invalid-name"), name);
                return null;
            }
            return role;
        }
    }
}

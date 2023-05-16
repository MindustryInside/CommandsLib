package inside.commands;

import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.params.BaseParameter;
import inside.commands.params.InvalidParameterException;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.ParameterKey;
import mindustry.gen.Player;

import java.util.Locale;

class CustomParamExample {
    static final CommandHandler handler = new CommandHandler("/");

    static final MandatoryKey<String> name = MandatoryKey.of("name", String.class);

    // map which contains admin names and their role
    static final ObjectMap<String, String> admins = new ObjectMap<>();

    static {
        addAdmin("Skat", "Fish");
        addAdmin("Dark", "Dark");
        addAdmin("Nekonya", "Nya~");
        addAdmin("Oct", "tcO");
    }

    static void addAdmin(String name, String role) {
        admins.put(name.toLowerCase(Locale.ROOT), role);
    }

    static String getRole(String name) {
        return admins.get(name.toLowerCase(Locale.ROOT));
    }

    public static void main(String[] args) {
        CommandManager manager = new CommandManager(handler);
        manager.register("get")
                .description("desc")
                .parameter(CustomParameter.from(name))
                .handler(ctx -> {
                    try {
                        String role = ctx.get(name);
                        Log.info("Found role: '@'", role);
                    } catch (AdminNotFoundException e) {
                        Log.err(e.localise(ctx.player()));
                    }
                });

        performCommand("/get skat");
        performCommand("/get anuke");
    }

    static void performCommand(String text) {
        var res = handler.handleMessage(text);
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
        public String localise(Player player) {
            return "Oh no! No admin with name '" + name + "' found!";
        }
    }
}

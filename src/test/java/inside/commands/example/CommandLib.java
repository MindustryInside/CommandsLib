package inside.commands.example;

import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.CommandManager;
import inside.commands.params.IntParameter;
import inside.commands.params.StringParameter;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.MandatorySingleKey;
import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.OptionalSingleKey;
import mindustry.gen.Player;

import java.util.Optional;

class CommandLib {
    static final CommandHandler commonHandler = new CommandHandler("/");

    static final MandatorySingleKey<String> name = MandatoryKey.single("name");
    static final OptionalSingleKey<Integer> age = OptionalKey.single("age");
    static final OptionalSingleKey<Player> target = OptionalKey.single("target");

    public static void main(String[] args) {
        CommandManager manager = new CommandManager(commonHandler, commonHandler);

        manager.registerServer("test")
                .description("description")
                .aliases("t", "cmd")
                .parameter(StringParameter.from(name))
                .parameter(IntParameter.from(age, -1)
                        .withInRange(13, 18))
                // .parameter(PlayerParameter.from(target)
                //         .withOptions(SearchOption.IGNORE_CASE, SearchOption.STRIP_COLORS_AND_GLYPHS))
                .handler(serverCtx -> {
                    String mandatory = serverCtx.get(name);
                    serverCtx.messageService().sendMessage("mandatory: {0}", mandatory);

                    int asserted = serverCtx.getAsserted(age);
                    Optional<Integer> optional = serverCtx.get(age);
                    int defaultValue = serverCtx.get(age, -1);
                    int defaultValueFromProv = serverCtx.getOrDefault(age, () -> -1);
                    serverCtx.messageService().sendMessage("optional: {0}", optional);

                    // Player targetValue = serverCtx.get(target, null);
                    // serverCtx.messageService().sendMessage("target: {0}", targetValue);
                });

        performCommand("/test t1 15");
        performCommand("/t t2");
    }

    static void performCommand(String text) {
        var res = commonHandler.handleMessage(text);
        if (res.type != CommandHandler.ResponseType.valid) {
            Log.info(res.type);
        }
    }
}

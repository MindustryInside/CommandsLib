package inside.commands.example;

import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.CommandManager;
import inside.commands.params.IntParameter;
import inside.commands.params.PlayerParameter;
import inside.commands.params.PlayerParameter.SearchOption;
import inside.commands.params.StringParameter;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.OptionalVariadicKey;
import mindustry.gen.Player;
import mindustry.mod.Plugin;

import java.util.Optional;

class CommandLib {
    static final CommandHandler commonHandler = new CommandHandler("/");

    static final MandatoryKey<String> name = MandatoryKey.of("name");
    static final OptionalKey<Integer> age = OptionalKey.of("age");
    static final OptionalKey<Player> target = OptionalKey.of("target");
    static final OptionalVariadicKey<Integer> dates = OptionalVariadicKey.of("dates");

    public static void main(String[] args) {
        CommandManager manager = new CommandManager(commonHandler, commonHandler);
        manager.registerServer("test")
                .description("description")
                .aliases("t", "cmd")
                .parameter(StringParameter.from(name))
                .parameter(IntParameter.from(age)
                        .withInRange(13, 18))
                // .parameter(PlayerParameter.from(target)
                //         .withOptions(SearchOption.IGNORE_CASE, SearchOption.STRIP_COLORS_AND_GLYPHS))
                .parameter(IntParameter.from(dates))
                .handler(serverCtx -> {
                    String mandatory = serverCtx.get(name);
                    serverCtx.messageService().sendMessage("mandatory: {0}", mandatory);

                    {
                        Optional<Integer> optional = serverCtx.get(age);
                        int defaultValue = serverCtx.get(age, -1);
                        int defaultValueFromProv = serverCtx.getOrDefault(age, () -> -1);
                        serverCtx.messageService().sendMessage("optional: {0}", optional);
                    }

                    // Player targetValue = serverCtx.get(target, null);
                    // serverCtx.messageService().sendMessage("target: {0}", targetValue);

                    Optional<Seq<Integer>> variadic = serverCtx.get(dates);
                    Seq<Integer> defaultValue = serverCtx.get(dates, Seq.with());
                    Seq<Integer> defaultValueFromProv = serverCtx.getOrDefault(dates, () -> new Seq<>());
                    serverCtx.messageService().sendMessage("variadic: {0}", variadic);
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

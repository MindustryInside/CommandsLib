package inside.commands.example;

import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.CommandManager;
import inside.commands.params.IntParameter;
import inside.commands.params.StringParameter;
import inside.commands.params.keys.*;
import mindustry.gen.Player;

import java.util.Optional;

class CommandLib {
    static final CommandHandler commonHandler = new CommandHandler("/");

    static final MandatorySingleKey<String> name = MandatoryKey.single("name");
    static final OptionalSingleKey<Integer> age = OptionalKey.single("age");
    static final OptionalSingleKey<Player> target = OptionalKey.single("target");
    static final OptionalVariadicKey<String> words = OptionalKey.variadic("words");

    public static void main(String[] args) {
        CommandManager manager = new CommandManager(commonHandler, commonHandler);

        manager.registerServer("test")
                .description("description")
                .aliases("t", "cmd")
                .parameter(StringParameter.from(name))
                .parameter(IntParameter.from(age, -1)
                        .withInRange(13, 18))
                .parameter(StringParameter.from(words))
                // .parameter(PlayerParameter.from(target)
                //         .withOptions(SearchOption.IGNORE_CASE, SearchOption.STRIP_COLORS_AND_GLYPHS))
                .handler(ctx -> {
                    String mandatory = ctx.get(name);
                    ctx.messageService().sendMessage("mandatory: {0}", mandatory);

                    int asserted = ctx.getOrThrow(age);
                    Optional<Integer> optional = ctx.get(age);
                    int defaultValue = ctx.get(age, -1);
                    int defaultValueFromProv = ctx.getOrDefault(age, () -> -1);
                    ctx.messageService().sendMessage("optional: {0}", optional);

                    // Player targetValue = ctx.get(target, null);
                    // ctx.messageService().sendMessage("target: {0}", targetValue);

                    String wordsv = ctx.get(words, ":(");
                    ctx.messageService().sendMessage("words: {0}", wordsv);
                });

        performCommand("/test t1 15");
        performCommand("/t t2 15 word1 word2 word3");
    }

    static void performCommand(String text) {
        var res = commonHandler.handleMessage(text);
        if (res.type != CommandHandler.ResponseType.valid) {
            Log.info(res.type);
        }
    }
}
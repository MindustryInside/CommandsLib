package inside.commands.example;

import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.CommandManager;
import inside.commands.params.IntParameter;
import inside.commands.params.InvalidNumberException;
import inside.commands.params.StringParameter;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.OptionalVariadicKey;

import java.util.Optional;

class CommandLib {
    static final MandatoryKey<String> name = MandatoryKey.of("name");
    static final OptionalKey<Integer> age = OptionalKey.of("age");
    static final OptionalVariadicKey<Integer> dates = OptionalVariadicKey.of("dates");

    public static void main(String[] args) {
        CommandHandler handler = new CommandHandler("/");
        CommandManager manager = new CommandManager(handler);
        manager.register("test")
                .description("description")
                .parameter(StringParameter.from(name))
                .parameter(IntParameter.from(age)
                        .withMinValue(13)
                        .withMaxValue(18))
                .parameter(IntParameter.from(dates))
                .handler(ctx -> {
                    String mandatory = ctx.get(name);
                    Log.info("mandatory: '@'", mandatory);

                    Optional<Integer> optional = ctx.get(age);
                    Log.info("optional: @", optional);

                    Optional<Seq<Integer>> variadic = ctx.get(dates);
                    Log.info("variadic: @", variadic);
                });

        var res = handler.handleMessage("/test text 12");
        if (res.type != CommandHandler.ResponseType.valid) {
            Log.info(res.type);
        }
    }
}

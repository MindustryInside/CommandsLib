package inside.commands;

import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.params.IntParameter;
import inside.commands.params.InvalidNumberException;
import inside.commands.params.StringParameter;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.OptionalVariadicKey;

import java.util.Optional;

class CommandLib {
    static final MandatoryKey<String> name = MandatoryKey.of("name", String.class);
    static final OptionalKey<Integer> age = OptionalKey.of("age", Integer.class);
    static final OptionalVariadicKey<Integer> dates = OptionalVariadicKey.of("dates", Integer.class);

    public static void main(String[] args) {
        CommandHandler handler = new CommandHandler("/");
        CommandManager manager = new CommandManager(handler);
        manager.register("test")
                .description("description")
                .parameter(StringParameter.from(name))
                .parameter(IntParameter.from(age))
                .parameter(IntParameter.from(dates))
                .handler(ctx -> {
                    String mandatory = ctx.get(name);
                    Log.info("mandatory: '@'", mandatory);

                    try {
                        Optional<Integer> optional = ctx.get(age);
                        Log.info("optional: @", optional);
                    } catch (InvalidNumberException e) {
                        String msg = e.localise(ctx.player());
                        Log.err("2> " + msg);
                    }

                    try {
                        Optional<Seq<Integer>> variadic = ctx.get(dates);
                        Log.info("variadic: @", variadic);
                    } catch (InvalidNumberException e) {
                        String msg = e.localise(ctx.player());
                        Log.err("3> " + msg);
                    }
                });

        var res = handler.handleMessage("/test text 3 44 4 45");
        if (res.type != CommandHandler.ResponseType.valid) {
            Log.info(res.type);
        }
    }
}

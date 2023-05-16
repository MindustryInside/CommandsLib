package inside.commands;

import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.params.IntParameter;
import inside.commands.params.InvalindNumberException;
import inside.commands.params.keys.MandatoryKey;
import inside.commands.params.keys.OptionalKey;
import inside.commands.params.keys.OptionalVariadicKey;

import java.util.Optional;

public final class CommandLib {
    static final MandatoryKey<Integer> amount = MandatoryKey.of("amount", Integer.class);
    static final OptionalKey<Integer> age = OptionalKey.of("age", Integer.class);
    static final OptionalVariadicKey<Integer> dates = OptionalVariadicKey.of("dates", Integer.class);

    public static void main(String[] args) {
        CommandHandler handler = new CommandHandler("/");
        CommandManager manager = new CommandManager(handler);
        manager.register("test")
                .description("description")
                .parameter(IntParameter.from(amount))
                .parameter(IntParameter.from(age))
                .parameter(IntParameter.from(dates))
                .handler(ctx -> {
                    try {
                        Integer mandatory = ctx.get(amount);
                        Log.info("mandatory: @", mandatory);
                    } catch (InvalindNumberException e) {
                        String msg = e.localise(ctx.player());
                        Log.err("1> " + msg);
                    }

                    try {
                        Optional<Integer> optional = ctx.get(age);
                        Log.info("optional: @", optional);
                    } catch (InvalindNumberException e) {
                        String msg = e.localise(ctx.player());
                        Log.err("2> " + msg);
                    }

                    try {
                        Optional<Seq<Integer>> variadic = ctx.get(dates);
                        Log.info("variadic: @", variadic);
                    } catch (InvalindNumberException e) {
                        String msg = e.localise(ctx.player());
                        Log.err("3> " + msg);
                    }
                });

        var res = handler.handleMessage("/test 1 3 44 4 45");
        if (res.type != CommandHandler.ResponseType.valid) {
            Log.info(res.type);
        }
    }
}

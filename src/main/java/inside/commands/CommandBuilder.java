package inside.commands;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Log;
import inside.commands.params.InvalidParameterException;
import inside.commands.params.Parameter;
import inside.commands.params.VariadicParameter;
import mindustry.gen.Player;

import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;

public final class CommandBuilder {
    private final CommandManager manager;
    private final String name;
    private final Seq<Parameter<?>> parameters = new Seq<>();

    private Cons<CommandContext> handler;
    private Seq<String> aliases;
    private boolean hadOptional;
    private String description;

    CommandBuilder(CommandManager manager, String name) {
        this.manager = manager;
        this.name = Objects.requireNonNull(name);
    }

    public CommandBuilder aliases(String... aliases) {
        this.aliases = Seq.with(aliases);
        return this;
    }

    public CommandBuilder aliases(Iterable<String> aliases) {
        this.aliases = Seq.with(aliases);
        return this;
    }

    public CommandBuilder description(String description){
        this.description = Objects.requireNonNull(description);
        return this;
    }

    public CommandBuilder parameter(Parameter<?> param){
        Objects.requireNonNull(param);
        if (hadOptional && !param.optional()) {
            throw new IllegalArgumentException("Mandatory parameter cant follow after optional one");
        }
        hadOptional = param.optional();

        if (parameters.contains(p -> p.name().equals(param.name()))) {
            throw new IllegalArgumentException("Parameter with name '" + param.name() + "' already exists");
        }

        // TODO: check whether is variadic parameter is the last one
        this.parameters.add(param);
        return this;
    }

    public void handler(Cons<CommandContext> handler) {
        this.handler = handler;

        StringJoiner paramSj = new StringJoiner(" ");
        for (var p : parameters) {
            paramSj.add(parameterAsString(p));
        }

        String paramString = paramSj.toString();

        manager.handler.register(name, paramString, description, this::run);
        if (aliases != null) {
            for (String alias : aliases) {
                manager.handler.register(alias, paramString, description, this::run);
            }
        }
    }

    private void run(String[] args, Player player) {
        Locale locale = player != null ? manager.bundleProvider.getLocale(player) : manager.locale;
        var parsedParams = new ObjectMap<String, Object>();
        for (int i = 0; i < args.length; i++) {
            var p = parameters.get(i);
            if (p instanceof VariadicParameter<?> v) {
                try {
                    parsedParams.put(p.name(), v.parseMultiple(args[i]));
                } catch (InvalidParameterException e) {
                    String msg = e.localise(locale);

                    Log.err(msg);
                    return;
                }
            } else {
                try {
                    parsedParams.put(p.name(), p.parse(args[i]));
                } catch (InvalidParameterException e) {
                    String msg = e.localise(locale);

                    Log.err(msg);
                    return;
                }
            }
        }

        handler.get(new CommandContext(locale, player, parsedParams));
    }

    static String parameterAsString(Parameter<?> param) {
        String s = param.name();
        if (param.variadic()) {
            s = s + "...";
        }

        if (param.optional()) {
            s = '[' + s + ']';
        } else {
            s = '<' + s + '>';
        }

        return s;
    }
}

package inside.commands;

import arc.struct.Seq;
import inside.commands.params.Parameter;

import java.util.Objects;

public abstract sealed class CommandBuilder permits ServerCommandBuilder, ClientCommandBuilder {
    final CommandManager manager;
    final String name;
    final Seq<Parameter<?>> parameters = new Seq<>();

    Seq<String> aliases;
    boolean hadOptional;
    String description = "";

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

    public CommandBuilder description(String description) {
        this.description = Objects.requireNonNull(description);
        return this;
    }

    public CommandBuilder parameter(Parameter<?> param) {
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

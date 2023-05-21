package inside.commands.menu;

import arc.struct.Seq;

import java.util.Objects;

public class MenuSpec {
    public String title = "";
    public String message = "";
    public Seq<Seq<OptionSpec<?>>> options = new Seq<>();

    public MenuSpec title(String title) {
        this.title = Objects.requireNonNull(title);
        return this;
    }

    public MenuSpec message(String message) {
        this.message = Objects.requireNonNull(message);
        return this;
    }

    public MenuSpec addRow(OptionSpec<?>... options) {
        this.options.add(Seq.with(options));
        return this;
    }

    public MenuSpec addRows(OptionSpec<?>[]... options) {
        for (var option : options) {
            this.options.add(Seq.with(option));
        }
        return this;
    }
}

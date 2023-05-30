package inside.commands.menu;

import arc.struct.Seq;

// TODO: может переименовать? Уж слишком просто перепутать с OptionSpec
public class OptionsSpec {
    public Seq<Seq<OptionSpec<?>>> options = new Seq<>();

    public OptionsSpec addRow(OptionSpec<?>... options) {
        this.options.add(Seq.with(options));
        return this;
    }

    public OptionsSpec addRows(OptionSpec<?>[]... options) {
        for (var option : options) {
            this.options.add(Seq.with(option));
        }
        return this;
    }
}

package inside.commands.menu;

import arc.func.Cons;

import java.util.Objects;

public class MenuSpec {
    public String title = "";
    public String message = "";

    public OptionsSpec optionsSpec;
    public TextInputSpec textInputSpec;

    public MenuSpec title(String title) {
        this.title = Objects.requireNonNull(title);
        return this;
    }

    public MenuSpec message(String message) {
        this.message = Objects.requireNonNull(message);
        return this;
    }

    public MenuSpec input(Cons<TextInputSpec> action) {
        if (optionsSpec != null) {
            throw new IllegalStateException("You cannot configure text input for option menu");
        }

        var tspec = textInputSpec;
        if (tspec == null) {
            textInputSpec = tspec = new TextInputSpec();
        }
        action.get(tspec);
        return this;
    }

    public MenuSpec options(Cons<OptionsSpec> action) {
        if (textInputSpec != null) {
            throw new IllegalStateException("You cannot configure options for text input menu");
        }

        var ospec = optionsSpec;
        if (ospec == null) {
            optionsSpec = ospec = new OptionsSpec();
        }
        action.get(ospec);
        return this;
    }
}
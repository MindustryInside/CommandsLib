package inside.commands.menu;

import java.util.Objects;

public class TextInputSpec {
    public int textLength;
    public String def = "";
    public boolean numeric;

    public TextInputSpec textLength(int textLength) {
        this.textLength = textLength;
        return this;
    }

    public TextInputSpec def(String def) {
        this.def = Objects.requireNonNull(def);
        return this;
    }

    public TextInputSpec numeric(boolean numeric) {
        this.numeric = numeric;
        return this;
    }
}

package inside.commands.menu;

import java.util.Objects;

public class OptionSpec<T> {
    public String text = "";
    public T value;

    public OptionSpec() {}

    public OptionSpec(String text, T value) {
        this.text = Objects.requireNonNull(text);
        this.value = value;
    }

    public static <T> OptionSpec<T> create() {
        return new OptionSpec<>();
    }

    public static <T> OptionSpec<T> create(String text, T value) {
        return new OptionSpec<>(text, value);
    }

    public OptionSpec<T> text(String text) {
        this.text = Objects.requireNonNull(text);
        return this;
    }

    public OptionSpec<T> value(T value) {
        this.value = value;
        return this;
    }
}

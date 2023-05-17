package inside.commands.params;

import java.util.Locale;

public class InvalidNumberException extends InvalidParameterException {

    private final String value;
    private final Type type;

    public InvalidNumberException(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String localise(Locale locale) {
        return switch (type) {
            case INVALID -> "String '" + value + "' is not a number";
            case LESS_MIN -> "Integer " + value + " is less than min value";
            case GREATER_MAX -> "Integer " + value + " is greater than max value";
        };
    }

    public enum Type {
        INVALID,
        LESS_MIN,
        GREATER_MAX
    }
}

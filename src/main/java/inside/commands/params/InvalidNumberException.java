package inside.commands.params;

import mindustry.gen.Player;

public class InvalidNumberException extends InvalidParameterException {

    private final String value;
    private final Type type;

    public InvalidNumberException(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String localise(Player player) {
        return switch (type) {
            case INVALID -> "String '" + value + "' is not a number";
            case GREATER_MAX -> "Integer " + value + " is greater than max value";
            case LESS_THAN_MIN -> "Integer " + value + " is less than min value";
        };
    }

    public enum Type {
        INVALID,
        LESS_THAN_MIN,
        GREATER_MAX
    }
}

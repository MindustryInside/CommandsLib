package inside.commands.params;

import inside.commands.MessageService;

public class InvalidNumberException extends InvalidParameterException {

    private final String value;
    private final Type type;

    public InvalidNumberException(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public void report(MessageService messageService) {
        messageService.sendError(switch (type) {
            case INVALID -> "String '@' is not a number";
            case LESS_MIN -> "Integer @ is less than min value";
            case GREATER_MAX -> "Integer @ is greater than max value";
        }, value);
    }

    public enum Type {
        INVALID,
        LESS_MIN,
        GREATER_MAX
    }
}

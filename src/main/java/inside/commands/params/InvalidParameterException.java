package inside.commands.params;

import inside.commands.MessageService;

public abstract class InvalidParameterException extends RuntimeException {

    public InvalidParameterException() {
        super(null, null, false, false);
    }

    public abstract void report(MessageService messageService);
}

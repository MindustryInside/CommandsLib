package inside.commands.params;

import inside.commands.MessageService;

public interface Parameter<T> {

    String name();

    boolean optional();

    boolean variadic();

    /**
     * Attempts to parse value from specified string argument.
     *
     * @param service The service to communicate with command sender.
     * @param value The string value of argument. May have spaces if parameter
     * is declared as {@link #variadic()}.
     * @return The result value of parameter, or {@code null} if it cannot be parsed.
     */
    T parse(MessageService service, String value);
}
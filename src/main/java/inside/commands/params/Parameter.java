package inside.commands.params;

import inside.commands.MessageService;

public interface Parameter<T> {

    String name();

    boolean optional();

    boolean variadic();

    T parse(MessageService service, String value);
}

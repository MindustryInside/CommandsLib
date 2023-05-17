package inside.commands.params;

import arc.struct.Seq;
import inside.commands.MessageService;

public interface VariadicParameter<T> extends Parameter<T> {

    Seq<T> parseMultiple(MessageService messageService, String value);
}

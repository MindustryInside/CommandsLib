package inside.commands.params;

import arc.struct.Seq;

public interface VariadicParameter<T> extends Parameter<T> {

    Seq<T> parseMultiple(String value) throws InvalidParameterException;
}

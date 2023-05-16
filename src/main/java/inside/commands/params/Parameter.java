package inside.commands.params;

public interface Parameter<T> {

    String name();

    boolean optional();

    boolean variadic();

    T parse(String value) throws InvalidParameterException;
}

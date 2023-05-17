package inside.commands.params;

import java.util.Locale;

public abstract class InvalidParameterException extends RuntimeException {

    public abstract String localise(Locale locale);
}

package inside.commands.params;

import mindustry.gen.Player;

public class InvalidNumberException extends InvalidParameterException {

    private final String value;

    public InvalidNumberException(String value) {
        this.value = value;
    }

    @Override
    public String localise(Player player) {
        return "String '" + value + "' is not a number";
    }
}

package inside.commands.params;

import mindustry.gen.Player;

public abstract class InvalidParameterException extends RuntimeException {

    public abstract String localise(Player player);
}

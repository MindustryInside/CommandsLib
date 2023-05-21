# CommandsLib

Library for Mindustry plugins and mods for convenient, safe and easy command registering.

## Example Command

```java
// Imports omitted
public class MyMod extends Mod {
    private static final MandatorySingleKey<Player> target = MandatoryKey.single("target");
    private static final MandatoryKey<String> text = MandatoryKey.variadic("text");

    private final CommandManager commandManager = new CommandManager();
    
    @Override
    public void registerServerCommands(CommandHandler handler) {
        commandManager.setServerHandler(handler);

        // creating command 'echo' with parameters '<text...>'
        commandManager.registerServer("echo")
                .description("Simple command to print specified text to log.")
                .parameter(StringParameter.from(text))
                .handler(ctx -> {
                    String message = ctx.get(text);
                    ctx.messageService().sendMessage("echo: {0}", message);
                });
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        commandManager.setClientHandler(handler);

        // creating command to send message to specified player
        commandManager.registerClient("dm")
                // /whisper will be alias for this command
                .aliases("whisper")
                .description("Write direct message for player.")
                .parameter(PlayerParameter.from(target)
                        // Allow to search players by ignoring case and colors in nick
                        .withOptions(SearchOption.IGNORE_CASE, SearchOption.STRIP_COLORS_AND_GLYPHS))
                .parameter(StringParameter.from(text))
                .handler(ctx -> {
                    Player targetPlayer = ctx.get(target);
                    String message = ctx.get(text);

                    String author = ctx.player().coloredName();
                    ctx.messageService().sendMessage(targetPlayer, "[accent](DM)[white] {0}[accent]:[white] {1}", author, message);
                });
    }
}
```

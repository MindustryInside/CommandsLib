package inside.commands;

import arc.struct.ObjectMap;
import inside.commands.menu.MenuSpec;
import inside.commands.params.ParameterWithMenuSupport;
import mindustry.gen.Call;

public class MenuContext {

    private final CommandManager manager;
    private final ClientMessageService messageService;
    private final ClientCommandInfo commandInfo;
    private final ParameterWithMenuSupport<?>[] parameters;
    private final ObjectMap<String, Object> processedParameters = new ObjectMap<>();

    private Object[] values;
    private int stage = 0;

    public MenuContext(CommandManager manager, ClientMessageService messageService, ClientCommandInfo commandInfo) {
        this.manager = manager;
        this.messageService = messageService;
        this.commandInfo = commandInfo;

        var params = commandInfo.parameters();
        this.parameters = new ParameterWithMenuSupport<?>[params.size];
        for (int i = 0; i < params.size; i++) {
            var p = params.get(i);
            if (p instanceof ParameterWithMenuSupport<?> m) {
                parameters[i] = m;
            } else {

                throw new UnsupportedOperationException();
            }
        }
    }

    void onClick(int option) {
        var currentParameter = parameters[stage];
        processedParameters.put(currentParameter.name(), values[option]);

        if (++stage == parameters.length) {
            manager.menuContexts.remove(messageService.player().uuid());
            commandInfo.handler().get(new ClientCommandContext(processedParameters, messageService));
            return;
        }

        createMenu();
    }

    public int currentStage() {
        return stage;
    }

    public int stages() {
        return parameters.length;
    }

    void createMenu() {
        var currentParameter = parameters[stage];
        MenuSpec spec = new MenuSpec();
        currentParameter.configure(this, spec);
        String title = messageService.bundle().format(messageService.locale(), spec.title, stage + 1);
        String message = messageService.bundle().format(messageService.locale(), spec.message, currentParameter.name());
        String[][] opts = new String[spec.options.size][];
        int p = 0;
        for (int y = 0; y < spec.options.size; y++) {
            var row = spec.options.get(y);
            String[] arr;
            opts[y] = arr = new String[row.size];

            for (int x = 0; x < row.size; x++) {
                arr[x] = row.get(x).text;
                p++;
            }
        }

        values = new Object[p];
        int d = 0;
        for (int y = 0; y < spec.options.size; y++) {
            var row = spec.options.get(y);
            for (int x = 0; x < row.size; x++) {
                values[d++] = row.get(x).value;
            }
        }
        Call.menu(messageService.player().con, manager.parameterMenuId, title, message, opts);
    }
}

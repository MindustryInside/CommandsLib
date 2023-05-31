package inside.commands;

import arc.struct.ObjectMap;
import inside.commands.menu.MenuSpec;
import inside.commands.params.DefaultValueParameter;
import inside.commands.params.MenuParameter;
import mindustry.gen.Call;

public class MenuContext {

    private final CommandManager manager;
    private final ClientMessageService messageService;
    private final ClientCommandDescriptor commandDescriptor;
    private final MenuParameter<?>[] parameters;
    private final ObjectMap<String, Object> processedParameters = new ObjectMap<>();

    private Object[] values;
    private int stage = 0;

    public MenuContext(CommandManager manager, ClientMessageService messageService, ClientCommandDescriptor commandDescriptor) {
        this.manager = manager;
        this.messageService = messageService;
        this.commandDescriptor = commandDescriptor;

        var params = commandDescriptor.info().parameters();
        this.parameters = new MenuParameter<?>[params.size];
        for (int i = 0; i < params.size; i++) {
            var p = params.get(i);
            if (p instanceof MenuParameter<?> m) {
                parameters[i] = m;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    void onClick(int option) {
        var currentParameter = parameters[stage];
        processedParameters.put(currentParameter.name(), values[option]);

        if (++stage == parameters.length) {
            manager.menuContexts.remove(messageService.player().uuid());
            commandDescriptor.handler().get(new ClientCommandContext(processedParameters, messageService));
            return;
        }

        createMenu();
    }

    void onTextInput(String text) {
        var currentParameter = parameters[stage];
        var o = currentParameter.parse(messageService, text);
        if (o != null) {
            processedParameters.put(currentParameter.name(), o);
        } else if (currentParameter instanceof DefaultValueParameter<?> d) {
            processedParameters.put(currentParameter.name(), d.getDefault());
        } else { // Параметр не пропарсился - идём ещё разок спрашиваем
            createMenu();
            return;
        }

        if (++stage == parameters.length) {
            manager.menuContexts.remove(messageService.player().uuid());
            commandDescriptor.handler().get(new ClientCommandContext(processedParameters, messageService));
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

    public int values() {
        return values.length;
    }

    void createMenu() {
        var currentParameter = parameters[stage];
        MenuSpec spec = new MenuSpec();
        currentParameter.configure(this, spec);

        // TODO: мне это не нравится. Скорее всего заменим на билдеры, где невозможно выстрелить себе в ноги
        if (spec.optionsSpec == null && spec.textInputSpec == null) {
            throw new IllegalStateException("Menu type is unspecified");
        }

        String title = messageService.bundle().format(spec.title, messageService.locale(),stage + 1);
        String message = messageService.bundle().format(spec.message, messageService.locale(), currentParameter.name());

        if (spec.textInputSpec != null) {
            Call.textInput(messageService.player().con, manager.parameterTextInputMenuId, title, message,
                    spec.textInputSpec.textLength, spec.textInputSpec.def, spec.textInputSpec.numeric);
        } else {
            // assert options.optionsSpec != null

            var options = spec.optionsSpec.options;
            String[][] opts = new String[options.size][];
            int p = 0;
            for (int y = 0; y < options.size; y++) {
                var row = options.get(y);
                String[] arr;
                opts[y] = arr = new String[row.size];

                for (int x = 0; x < row.size; x++) {
                    arr[x] = row.get(x).text;
                    p++;
                }
            }

            values = new Object[p];
            int d = 0;
            for (int y = 0; y < options.size; y++) {
                var row = options.get(y);
                for (int x = 0; x < row.size; x++) {
                    values[d++] = row.get(x).value;
                }
            }

            Call.menu(messageService.player().con, manager.parameterMenuId, title, message, opts);
        }
    }
}
package inside.commands.params;

import inside.commands.MenuContext;
import inside.commands.menu.MenuSpec;

// TODO: concise name
public interface ParameterWithMenuSupport<T> extends Parameter<T> {

    void configure(MenuContext context, MenuSpec spec);
}

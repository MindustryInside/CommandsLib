package inside.commands.params;

import inside.commands.MenuContext;
import inside.commands.menu.MenuSpec;

public interface MenuParameter<T> extends Parameter<T> {

    void configure(MenuContext context, MenuSpec spec);
}
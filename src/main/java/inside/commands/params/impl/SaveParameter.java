package inside.commands.params.impl;

import arc.files.Fi;
import inside.commands.MessageService;
import inside.commands.params.BaseSearchParameter;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.*;

import java.util.*;

public class SaveParameter extends BaseSearchParameter<Fi> {

    protected SaveParameter(SingleKey<Fi> key) {
        super(key);
    }

    protected SaveParameter(SingleKey<Fi> key, Set<SearchOption> options) {
        super(key, options);
    }

    public static SaveParameter from(SingleKey<Fi> key) {
        return new SaveParameter(key);
    }

    public static SaveParameter from(SingleKey<Fi> key, Set<SearchOption> options) {
        return new SaveParameter(key, options);
    }

    @Override
    public Fi parse(MessageService service, String value) {
        var saves = Search.saves(value, options);
        if (saves.isEmpty()) {
            service.sendError(service.prefix("save-not-found"), value);
            return null;
        } else if (saves.size > 1) {
            service.sendError(service.prefix("too-many-saves-found"), value);
            return null;
        }

        return saves.first();
    }
}
package inside.commands.params.impl;

import arc.files.Fi;
import inside.commands.MessageService;
import inside.commands.params.BaseParameter;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.*;

import java.util.*;

public class SaveParameter extends BaseParameter<Fi> {

    protected final Set<SearchOption> options;

    protected SaveParameter(SingleKey<Fi> key) {
        this(key, EnumSet.allOf(SearchOption.class));
    }

    protected SaveParameter(SingleKey<Fi> key, Set<SearchOption> options) {
        super(key);
        this.options = options;
    }

    protected SaveParameter(SaveParameter copy, Set<SearchOption> options) {
        super(copy);
        this.options = options;
    }

    public static SaveParameter from(SingleKey<Fi> key) {
        return new SaveParameter(key);
    }

    public static SaveParameter from(SingleKey<Fi> key, Set<SearchOption> options) {
        return new SaveParameter(key, options);
    }

    public Set<SearchOption> options() {
        return options;
    }

    public SaveParameter withOptions(SearchOption... options) {
        var newOptions = EnumSet.copyOf(Arrays.asList(options));
        if (newOptions.equals(this.options)) return this;

        return new SaveParameter(this, newOptions);
    }

    public SaveParameter withOptions(Collection<SearchOption> options) {
        var newOptions = EnumSet.copyOf(options);
        if (newOptions.equals(this.options)) return this;

        return new SaveParameter(this, newOptions);
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

    @Override
    public String toString() {
        return "SaveParameter{" +
                "options=" + options +
                ", name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                '}';
    }
}
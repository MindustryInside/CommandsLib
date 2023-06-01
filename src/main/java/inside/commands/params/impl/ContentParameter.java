package inside.commands.params.impl;

import inside.commands.MessageService;
import inside.commands.params.BaseSearchParameter;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.*;
import mindustry.ctype.*;

import java.util.*;

public class ContentParameter<T extends UnlockableContent> extends BaseSearchParameter<T> {
    protected final ContentType type;

    protected ContentParameter(SingleKey<T> key, ContentType type) {
        super(key);
        this.type = type;
    }

    protected ContentParameter(SingleKey<T> key, ContentType type, Set<SearchOption> options) {
        super(key, options);
        this.type = type;
    }

    @Override
    public T parse(MessageService service, String value) {
        var content = Search.<T>content(value, type, options);
        if (content == null)
            service.sendError(service.prefix(type.name() + "-not-found"), value);

        return content;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
                "name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                ", options=" + options +
                ", type=" + type +
                '}';
    }
}
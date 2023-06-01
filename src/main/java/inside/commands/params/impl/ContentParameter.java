package inside.commands.params.impl;

import inside.commands.MessageService;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.*;
import mindustry.ctype.*;

import java.util.*;

public class ContentParameter<T extends UnlockableContent> extends SearchParameter<T> {
    protected final ContentType type;

    protected ContentParameter(SingleKey<T> key, ContentType type) {
        super(key);
        this.type = type;
    }

    protected ContentParameter(SingleKey<T> key, ContentType type, Set<SearchOption> options) {
        super(key, options);
        this.type = type;
    }

    protected ContentParameter(ContentParameter<T> copy, Set<SearchOption> options) {
        super(copy, options);
        this.type = copy.type;
    }

    @Override
    public T parse(MessageService service, String value) {
        var content = Search.<T>content(value, type, options);
        if (content == null)
            service.sendError(service.prefix(type.name() + "-not-found"), value);

        return content;
    }

    @Override
    public ContentParameter<T> withOptions(SearchOption... options) {
        var newOptions = EnumSet.copyOf(Arrays.asList(options));
        if (newOptions.equals(this.options)) return this;

        return new ContentParameter<>(this, newOptions);
    }

    @Override
    public ContentParameter<T> withOptions(Collection<SearchOption> options) {
        var newOptions = EnumSet.copyOf(options);
        if (newOptions.equals(this.options)) return this;

        return new ContentParameter<>(this, newOptions);
    }

    @Override
    public String toString() {
        return "ContentParameter{" +
                "type=" + type +
                ", options=" + options +
                ", name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                '}';
    }
}
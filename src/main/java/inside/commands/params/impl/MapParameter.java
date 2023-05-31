package inside.commands.params.impl;

import inside.commands.MessageService;
import inside.commands.params.BaseParameter;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.*;
import mindustry.maps.Map;

import java.util.*;

public class MapParameter extends BaseParameter<Map> {

    protected final Set<SearchOption> options;

    protected MapParameter(SingleKey<Map> key) {
        this(key, EnumSet.allOf(SearchOption.class));
    }

    protected MapParameter(SingleKey<Map> key, Set<SearchOption> options) {
        super(key);
        this.options = options;
    }

    protected MapParameter(MapParameter copy, Set<SearchOption> options) {
        super(copy);
        this.options = options;
    }

    public static MapParameter from(SingleKey<Map> key) {
        return new MapParameter(key);
    }

    public static MapParameter from(SingleKey<Map> key, Set<SearchOption> options) {
        return new MapParameter(key, options);
    }

    public Set<SearchOption> options() {
        return options;
    }

    public MapParameter withOptions(SearchOption... options) {
        var newOptions = EnumSet.copyOf(Arrays.asList(options));
        if (newOptions.equals(this.options)) return this;

        return new MapParameter(this, newOptions);
    }

    public MapParameter withOptions(Collection<SearchOption> options) {
        var newOptions = EnumSet.copyOf(options);
        if (newOptions.equals(this.options)) return this;

        return new MapParameter(this, newOptions);
    }

    @Override
    public Map parse(MessageService service, String value) {
        var maps = Search.maps(value, options);
        if (maps.isEmpty()) {
            service.sendError(service.prefix("map-not-found"), value);
            return null;
        } else if (maps.size > 1) {
            service.sendError(service.prefix("too-many-maps-found"), value);
            return null;
        }

        return maps.first();
    }

    @Override
    public String toString() {
        return "MapParameter{" +
                "options=" + options +
                ", name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                '}';
    }
}
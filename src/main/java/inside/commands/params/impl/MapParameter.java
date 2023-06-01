package inside.commands.params.impl;

import inside.commands.MessageService;
import inside.commands.params.BaseSearchParameter;
import inside.commands.params.keys.SingleKey;
import inside.commands.util.*;
import mindustry.maps.Map;

import java.util.*;

public class MapParameter extends BaseSearchParameter<Map> {

    protected MapParameter(SingleKey<Map> key) {
        super(key);
    }

    protected MapParameter(SingleKey<Map> key, Set<SearchOption> options) {
        super(key, options);
    }

    public static MapParameter from(SingleKey<Map> key) {
        return new MapParameter(key);
    }

    public static MapParameter from(SingleKey<Map> key, Set<SearchOption> options) {
        return new MapParameter(key, options);
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
}
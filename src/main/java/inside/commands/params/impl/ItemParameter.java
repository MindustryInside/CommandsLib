package inside.commands.params.impl;

import inside.commands.params.keys.SingleKey;
import inside.commands.util.SearchOption;
import mindustry.ctype.ContentType;
import mindustry.type.*;

import java.util.*;

public class ItemParameter extends ContentParameter<Item> {

    protected ItemParameter(SingleKey<Item> key) {
        super(key, ContentType.item);
    }

    protected ItemParameter(SingleKey<Item> key, Set<SearchOption> options) {
        super(key, ContentType.item, options);
    }

    public static ItemParameter from(SingleKey<Item> key) {
        return new ItemParameter(key);
    }

    public static ItemParameter from(SingleKey<Item> key, Set<SearchOption> options) {
        return new ItemParameter(key, options);
    }
}
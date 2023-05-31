package inside.commands.params.impl;

import inside.commands.params.keys.SingleKey;
import inside.commands.util.SearchOption;
import mindustry.ctype.ContentType;
import mindustry.type.*;

import java.util.*;

public class StatusParameter extends ContentParameter<StatusEffect> {

    protected StatusParameter(SingleKey<StatusEffect> key) {
        super(key, ContentType.status);
    }

    protected StatusParameter(SingleKey<StatusEffect> key, Set<SearchOption> options) {
        super(key, ContentType.status, options);
    }

    protected StatusParameter(ContentParameter<StatusEffect> copy, Set<SearchOption> options) {
        super(copy, options);
    }

    public static StatusParameter from(SingleKey<StatusEffect> key) {
        return new StatusParameter(key);
    }

    public static StatusParameter from(SingleKey<StatusEffect> key, Set<SearchOption> options) {
        return new StatusParameter(key, options);
    }
}
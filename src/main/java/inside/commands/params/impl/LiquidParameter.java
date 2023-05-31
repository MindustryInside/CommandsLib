package inside.commands.params.impl;

import inside.commands.params.keys.SingleKey;
import inside.commands.util.SearchOption;
import mindustry.ctype.ContentType;
import mindustry.type.*;

import java.util.*;

public class LiquidParameter extends ContentParameter<Liquid> {

    protected LiquidParameter(SingleKey<Liquid> key) {
        super(key, ContentType.liquid);
    }

    protected LiquidParameter(SingleKey<Liquid> key, Set<SearchOption> options) {
        super(key, ContentType.liquid, options);
    }

    protected LiquidParameter(ContentParameter<Liquid> copy, Set<SearchOption> options) {
        super(copy, options);
    }

    public static LiquidParameter from(SingleKey<Liquid> key) {
        return new LiquidParameter(key);
    }

    public static LiquidParameter from(SingleKey<Liquid> key, Set<SearchOption> options) {
        return new LiquidParameter(key, options);
    }
}
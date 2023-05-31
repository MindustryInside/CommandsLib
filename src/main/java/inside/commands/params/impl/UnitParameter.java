package inside.commands.params.impl;

import inside.commands.params.keys.SingleKey;
import inside.commands.util.SearchOption;
import mindustry.ctype.ContentType;
import mindustry.type.*;

import java.util.Set;

public class UnitParameter extends ContentParameter<UnitType> {

    protected UnitParameter(SingleKey<UnitType> key) {
        super(key, ContentType.unit);
    }

    protected UnitParameter(SingleKey<UnitType> key, Set<SearchOption> options) {
        super(key, ContentType.unit, options);
    }

    protected UnitParameter(ContentParameter<UnitType> copy, Set<SearchOption> options) {
        super(copy, options);
    }

    public static UnitParameter from(SingleKey<UnitType> key) {
        return new UnitParameter(key);
    }

    public static UnitParameter from(SingleKey<UnitType> key, Set<SearchOption> options) {
        return new UnitParameter(key, options);
    }
}
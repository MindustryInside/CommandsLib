package inside.commands.params.impl;

import inside.commands.params.keys.SingleKey;
import inside.commands.util.SearchOption;
import mindustry.ctype.ContentType;
import mindustry.world.Block;

import java.util.Set;

public class BlockParameter extends ContentParameter<Block> {

    protected BlockParameter(SingleKey<Block> key) {
        super(key, ContentType.block);
    }

    protected BlockParameter(SingleKey<Block> key, Set<SearchOption> options) {
        super(key, ContentType.block, options);
    }

    public static BlockParameter from(SingleKey<Block> key) {
        return new BlockParameter(key);
    }

    public static BlockParameter from(SingleKey<Block> key, Set<SearchOption> options) {
        return new BlockParameter(key, options);
    }
}
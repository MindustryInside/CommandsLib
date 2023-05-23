package inside.commands.util;

// TODO каждый enum передвинуть внутрь класса соответствующего параметра
public interface SearchOption {
    enum DefaultSearchOption implements SearchOption {
        USE_ID,
        IGNORE_CASE,
        IGNORE_COLORS,
        IGNORE_EMOJI,
        USE_CONTAINS
    }

    enum PlayerSearchOption implements SearchOption {
        USE_UUID,
        USE_IP
    }

    enum MapSearchOption implements SearchOption {
        CUSTOM_MAPS,
        BUILTIN_MAPS
    }
}
package inside.commands.params.impl;

import inside.commands.params.BaseParameter;
import inside.commands.params.keys.*;
import inside.commands.util.SearchOption;

import java.util.*;

public abstract class SearchParameter<T> extends BaseParameter<T> {
    protected final Set<SearchOption> options;

    protected SearchParameter(SingleKey<T> key) {
        this(key, EnumSet.allOf(SearchOption.class));
    }

    protected SearchParameter(SingleKey<T> key, Set<SearchOption> options) {
        super(key);
        this.options = options;
    }

    protected SearchParameter(SearchParameter<T> copy, Set<SearchOption> options) {
        super(copy);
        this.options = options;
    }

    public Set<SearchOption> options() {
        return options;
    }

    public abstract SearchParameter<T> withOptions(SearchOption... options);
    public abstract SearchParameter<T> withOptions(Collection<SearchOption> options);
}
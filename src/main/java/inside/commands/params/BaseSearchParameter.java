package inside.commands.params;

import inside.commands.params.keys.*;
import inside.commands.util.SearchOption;

import java.util.*;

public abstract class BaseSearchParameter<T> extends BaseParameter<T> {
    protected Set<SearchOption> options;

    protected BaseSearchParameter(SingleKey<T> key) {
        this(key, EnumSet.allOf(SearchOption.class));
    }

    protected BaseSearchParameter(SingleKey<T> key, Set<SearchOption> options) {
        super(key);
        this.options = options;
    }

    public Set<SearchOption> options() {
        return options;
    }

    public BaseSearchParameter<T> withOptions(SearchOption option, SearchOption... options) {
        this.options = EnumSet.of(option, options);
        return this;
    }

    public BaseSearchParameter<T> withOptions(Collection<SearchOption> options) {
        this.options = EnumSet.copyOf(options);
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
                "name='" + name + '\'' +
                ", optional=" + optional +
                ", variadic=" + variadic +
                ", options=" + options +
                '}';
    }
}
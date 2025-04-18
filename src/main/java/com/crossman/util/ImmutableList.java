package com.crossman.util;

import java.util.*;
import java.util.stream.Stream;

public final class ImmutableList<T> extends AbstractImmutableCollection<T, List<T>, ImmutableList<T>> {
    private ImmutableList(List<T> delegate) {
        super(delegate, ArrayList::new, ImmutableList::new);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ImmutableList<?> that) {
            return Objects.equals(this.delegate, that.delegate);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.delegate);
    }

    public static <T> ImmutableList<T> copyOf(Collection<T> coll) {
        return new ImmutableList<>(new ArrayList<>(coll));
    }

    @SafeVarargs
    public static <T> ImmutableList<T> of(T first, T... rest) {
        return new ImmutableList<>(Stream.concat(Stream.of(first), Stream.of(rest)).toList());
    }

    public static <T> ImmutableList<T> empty() {
        return new ImmutableList<>(Collections.emptyList());
    }
}

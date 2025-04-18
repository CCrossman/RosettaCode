package com.crossman.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ImmutableNavigableSet<T> extends AbstractImmutableCollection<T, NavigableSet<T>, ImmutableNavigableSet<T>> {
    private ImmutableNavigableSet(NavigableSet<T> delegate) {
        super(delegate, TreeSet::new, ImmutableNavigableSet::new);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ImmutableNavigableSet<?> that) {
            return Objects.equals(this.delegate, that.delegate);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.delegate);
    }

    public static <T> ImmutableNavigableSet<T> copyOf(Collection<T> coll) {
        return new ImmutableNavigableSet<>(new TreeSet<>(coll));
    }

    @SafeVarargs
    public static <T> ImmutableNavigableSet<T> of(T first, T... rest) {
        var mutableSet = Stream
                .concat(Stream.of(first), Stream.of(rest))
                .collect(Collectors.toCollection(TreeSet::new));
        return new ImmutableNavigableSet<>(mutableSet);
    }

    public static <T> ImmutableNavigableSet<T> empty() {
        return new ImmutableNavigableSet<>(Collections.emptyNavigableSet());
    }
}

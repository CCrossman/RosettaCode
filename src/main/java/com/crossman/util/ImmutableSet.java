package com.crossman.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ImmutableSet<T> extends AbstractImmutableCollection<T, Set<T>, ImmutableSet<T>> {
    private ImmutableSet(Set<T> delegate) {
        super(delegate, HashSet::new, ImmutableSet::new);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ImmutableSet<?> that) {
            return Objects.equals(this.delegate, that.delegate);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.delegate);
    }

    public static <T> ImmutableSet<T> copyOf(Collection<T> coll) {
        return new ImmutableSet<>(new HashSet<>(coll));
    }

    @SafeVarargs
    public static <T> ImmutableSet<T> of(T first, T... rest) {
        return new ImmutableSet<>(Stream.concat(Stream.of(first), Stream.of(rest)).collect(Collectors.toSet()));
    }

    public static <T> ImmutableSet<T> empty() {
        return new ImmutableSet<>(Collections.emptySet());
    }
}

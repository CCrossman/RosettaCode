package com.crossman.util;

import java.util.*;

public final class ImmutableNavigableMap<K,V> extends AbstractImmutableMap<K, V, NavigableMap<K,V>, ImmutableNavigableMap<K,V>> {
    private ImmutableNavigableMap(NavigableMap<K, V> delegate) {
        super(delegate, TreeMap::new, ImmutableNavigableMap::new);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ImmutableNavigableMap<?,?> that) {
            return Objects.equals(this.delegate, that.delegate);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.delegate);
    }

    public static <K,V> ImmutableNavigableMap<K,V> copyOf(Map<K,V> map) {
        return new ImmutableNavigableMap<>(new TreeMap<>(map));
    }

    public static <K,V> ImmutableNavigableMap<K,V> empty() {
        return new ImmutableNavigableMap<>(Collections.emptyNavigableMap());
    }
}

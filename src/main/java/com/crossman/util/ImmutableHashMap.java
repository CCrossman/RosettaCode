package com.crossman.util;

import java.util.*;

public final class ImmutableHashMap<K,V> extends AbstractImmutableMap<K, V, Map<K,V>, ImmutableHashMap<K,V>> {
    private ImmutableHashMap(Map<K, V> delegate) {
        super(delegate, HashMap::new, ImmutableHashMap::new);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ImmutableHashMap<?,?> that) {
            return Objects.equals(this.delegate, that.delegate);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.delegate);
    }

    public static <K,V> ImmutableHashMap<K,V> copyOf(Map<K,V> map) {
        return new ImmutableHashMap<>(new HashMap<>(map));
    }

    public static <K,V> ImmutableHashMap<K,V> empty() {
        return new ImmutableHashMap<>(Collections.emptyMap());
    }
}

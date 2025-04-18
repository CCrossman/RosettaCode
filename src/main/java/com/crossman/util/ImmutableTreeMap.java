package com.crossman.util;

import java.util.*;

public final class ImmutableTreeMap<K,V> extends AbstractImmutableMap<K, V, Map<K,V>, ImmutableTreeMap<K,V>> {
    private ImmutableTreeMap(Map<K, V> delegate) {
        super(delegate, TreeMap::new, ImmutableTreeMap::new);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ImmutableTreeMap<?,?> that) {
            return Objects.equals(this.delegate, that.delegate);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.delegate);
    }

    public static <K,V> ImmutableTreeMap<K,V> copyOf(Map<K,V> map) {
        return new ImmutableTreeMap<>(new TreeMap<>(map));
    }

    public static <K,V> ImmutableTreeMap<K,V> empty() {
        return new ImmutableTreeMap<>(Collections.emptyMap());
    }
}

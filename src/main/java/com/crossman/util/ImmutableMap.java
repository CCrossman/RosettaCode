package com.crossman.util;

import java.util.AbstractMap;
import java.util.function.BiConsumer;

public interface ImmutableMap<K,V> {

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    boolean containsKey(K key);

    boolean containsValue(V value);

    V get(K key);

    ImmutableSet<K> keySet();

    ImmutableCollection<V> values();

    ImmutableSet<AbstractMap.SimpleImmutableEntry<K, V>> entrySet();

    boolean equals(Object o);

    int hashCode();

    V getOrDefault(K key, V defaultValue);

    void forEach(BiConsumer<? super K, ? super V> action);
}

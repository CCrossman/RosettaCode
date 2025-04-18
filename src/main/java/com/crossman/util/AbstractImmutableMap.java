package com.crossman.util;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public abstract class AbstractImmutableMap<K,V,C1 extends Map<K,V>,C2 extends ThawableMap<K,V,C1>> implements ThawableMap<K,V,C1>{
    protected final C1 delegate;
    private final Supplier<C1> constructor;
    private final Function<C1,C2> freezer;

    protected AbstractImmutableMap(C1 delegate, Supplier<C1> constructor, Function<C1,C2> freezer) {
        this.delegate = requireNonNull(delegate);
        this.constructor = requireNonNull(constructor);
        this.freezer = requireNonNull(freezer);
    }

    public final int size() {
        return delegate.size();
    }

    public final boolean containsKey(K key) {
        return delegate.containsKey(key);
    }

    public final boolean containsValue(V value) {
        return delegate.containsValue(value);
    }

    public final V get(K key) {
        return delegate.get(key);
    }

    public final ImmutableSet<K> keySet() {
        return ImmutableSet.copyOf(delegate.keySet());
    }

    public final ImmutableCollection<V> values() {
        return ImmutableList.copyOf(delegate.values());
    }

    public final ImmutableSet<AbstractMap.SimpleImmutableEntry<K, V>> entrySet() {
        return ImmutableSet.copyOf(delegate.entrySet().stream()
            .map(entry -> {
                if (entry instanceof AbstractMap.SimpleImmutableEntry<K,V> sie) {
                    return sie;
                }
                var key = entry.getKey();
                var value = entry.getValue();
                return new AbstractMap.SimpleImmutableEntry<K,V>(key,value);
            })
            .collect(Collectors.toSet()));
    }

    public final V getOrDefault(K key, V defaultValue) {
        return delegate.getOrDefault(key,defaultValue);
    }

    public final void forEach(BiConsumer<? super K, ? super V> action) {
        delegate.forEach(action);
    }

    public final C2 thaw(Consumer<C1> blk) {
        var mutableMap = constructor.get();
        mutableMap.putAll(delegate);
        blk.accept(mutableMap);
        return freezer.apply(mutableMap);
    }

    public abstract boolean equals(Object o);
    public abstract int hashCode();
}

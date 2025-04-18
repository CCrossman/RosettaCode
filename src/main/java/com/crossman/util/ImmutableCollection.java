package com.crossman.util;

import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public interface ImmutableCollection<T> {
    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    boolean contains(T t);

    Object[] toArray();

    <U> U[] toArray(U[] a);

    boolean containsAll(Collection<T> c);

    <U> U[] toArray(IntFunction<U[]> generator);

    Spliterator<T> spliterator();

    Stream<T> stream();

    Stream<T> parallelStream();

    void forEach(Consumer<? super T> action);
}

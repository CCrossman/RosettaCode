package com.crossman.util;

import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public abstract class AbstractImmutableCollection<T, C1 extends Collection<T>, C2 extends ThawableCollection<T,C1>> implements ThawableCollection<T,C1> {
    protected final C1 delegate;
    private final Supplier<C1> constructor;
    private final Function<C1,C2> freezer;

    protected AbstractImmutableCollection(C1 delegate, Supplier<C1> constructor, Function<C1,C2> freezer) {
        this.delegate = requireNonNull(delegate);
        this.constructor = requireNonNull(constructor);
        this.freezer = requireNonNull(freezer);
    }

    @Override
    public final int size() {
        return delegate.size();
    }

    @Override
    public final boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Override
    public final Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public final <U> U[] toArray(U[] a) {
        return delegate.toArray(a);
    }

    @Override
    public final boolean containsAll(Collection<T> c) {
        return delegate.contains(c);
    }

    @Override
    public final <U> U[] toArray(IntFunction<U[]> generator) {
        return delegate.toArray(generator);
    }

    @Override
    public final Spliterator<T> spliterator() {
        return delegate.spliterator();
    }

    @Override
    public final Stream<T> stream() {
        return delegate.stream();
    }

    @Override
    public final Stream<T> parallelStream() {
        return delegate.parallelStream();
    }

    @Override
    public final void forEach(Consumer<? super T> action) {
        delegate.forEach(action);
    }

    @Override
    public final C2 thaw(Consumer<C1> blk) {
        var mutableCollection = constructor.get();
        mutableCollection.addAll(delegate);
        blk.accept(mutableCollection);
        return freezer.apply(mutableCollection);
    }

    public abstract boolean equals(Object o);
    public abstract int hashCode();

    @Override
    public /* open */ String toString() {
        return "[" + delegate.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "]";
    }
}

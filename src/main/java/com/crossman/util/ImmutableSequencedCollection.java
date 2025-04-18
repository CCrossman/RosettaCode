package com.crossman.util;

import java.util.Collection;
import java.util.function.Consumer;

public interface ImmutableSequencedCollection<T,C extends Collection<T>> extends ThawableCollection<T,C> {

    T getFirst();

    T getLast();

    ImmutableSequencedCollection<T,C> thaw(Consumer<C> blk);
}

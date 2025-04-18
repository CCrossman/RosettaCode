package com.crossman.util;

import java.util.Collection;
import java.util.function.Consumer;

public interface ThawableCollection<T,C extends Collection<T>> extends ImmutableCollection<T> {
    ThawableCollection<T,C> thaw(Consumer<C> blk);
}

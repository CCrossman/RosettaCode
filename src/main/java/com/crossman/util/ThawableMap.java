package com.crossman.util;

import java.util.Map;
import java.util.function.Consumer;

public interface ThawableMap<K,V,C extends Map<K,V>> extends ImmutableMap<K,V > {
    ThawableMap<K,V,C> thaw(Consumer<C> blk);
}

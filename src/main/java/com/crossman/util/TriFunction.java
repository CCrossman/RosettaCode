package com.crossman.util;

@FunctionalInterface
public interface TriFunction<A,B,C,D> {
    public D apply(A first, B second, C third);
}

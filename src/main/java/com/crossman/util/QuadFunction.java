package com.crossman.util;

@FunctionalInterface
public interface QuadFunction<A,B,C,D,E> {
    public E apply(A first, B second, C third, D fourth);
}
